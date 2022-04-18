import SparkMD5 from 'spark-md5'
import axios from "axios";
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";
import ajax from "./ajax";

const apis = {
  uploadInit: "/fileApi/upload/initUpload.token",
  upload: "/fileApi/upload/upload.token"
}

const upload = function (file, status) {
  let fileReader = new FileReader()
  let Spark = new SparkMD5.ArrayBuffer()
  fileReader.readAsArrayBuffer(file)
  fileReader.onload = function (e) {
    Spark.append(e.target.result)
    uploadInit(file, Spark.end(), status)
  }
}

const uploadInit = function (file, md5, status) {
  axios.post(
    apis.uploadInit, {
      fileName: file.name,
      md5: md5,
      allSize: file.size
    }, {
      headers: {
        token: common.User.token
      }
    }).then(data => {
    data = data.data
    if (data.status === 100) {
      data = data.data
      doUpload(data.uuid, file, data.from, data.to, status)
    } else if (data.status === 101) {
      status(100)
      Vue.use(Message.success("上传成功"))
      ajax.getFileList()
    }
  }, data => {
  })
}

const doUpload = function (uuid, file, from, to, status) {
  status((from / file.size) * 100)
  let data = new FormData()
  data.append("uuid", uuid)
  data.append('file', file.slice(from, to))
  uplodOne(
    apis.upload, data, data => {
      doUpload(uuid, file, data.from, data.to, status)
    }, () => {
      status(100)
    }
  )
}

const uplodOne = function (url, data, ok, done, error) {
  axios.post(url, data, {
      headers: {
        "Content-Type": "multipart/form-data; boundary=----WebKitFormBoundaryfMpiwYVb3Eliuo7t",
        token: common.User.token
      }
    }
  ).then(res => {
    res = res.data
    if (res.status === 101) {
      done()
      Vue.use(Message.success("上传成功"))
      ajax.getFileList()
    } else if (res.status === 100) {
      if (ok !== null)
        ok(res.data)
    } else if (res.status === 400) {
      if (error !== null)
        error(res.data)
    } else if (res.status === 401) {
      Vue.use(Message.error("Token 失效，请重新登录"))
      common.User.reset()
    } else if (res.status >= 500) {
      Vue.use(Message.error("服务器开小差"))
    }
  }).catch(res => {
    console.log(res)
  })
}

export default {upload}
