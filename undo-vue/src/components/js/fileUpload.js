import SparkMD5 from 'spark-md5'
import axios from "axios";
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";

const apis = {
  uploadInit: "/fileApi/upload/initUpload",
  upload: "/fileApi/upload/upload"
}

const upload = function (file) {
  let fileReader = new FileReader()
  let Spark = new SparkMD5.ArrayBuffer()
  fileReader.readAsArrayBuffer(file)
  fileReader.onload = function (e) {
    Spark.append(e.target.result)
    uploadInit(file, Spark.end())
  }
}

const uploadInit = function (file, md5) {
  POST(
    apis.uploadInit, {}, {
      fileName: file.name,
      md5: md5,
      allSize: file.size
    },
    data => {
      doUpload(data.uuid, file, data.from, data.to)
    }, data => {
      console.log(data)
    }
  )
}

const doUpload = function (uuid, file, from, to) {
  let data = new FormData()
  data.append("uuid", uuid)
  data.append('file', file.slice(from, to))
  POST(
    apis.upload,
    {
      "Content-Type": "multipart/form-data; boundary=----WebKitFormBoundaryfMpiwYVb3Eliuo7t"
    }, data, data => {
      doUpload(uuid, file, data.from, data.to)
    }
  )
}

const POST = function (url, header, data, ok, error) {
  axios.post(url, data, {
      headers: header
    }
  ).then(res => {
    res = res.data
    if (res.status === 101) {
      Vue.use(Message.success("上传成功"))
    } else if (res.status === 100 || res.status === 200) {
      if (ok !== null)
        ok(res.data)
    } else if (res.status === 400) {
      if (error !== null)
        error(res.data)
    } else if (res.status === 401) {
      Vue.use(Message.error("Token 失效，请重新登录"))
      common.User.reset()
    } else if (res.status === 402) {
      Vue.use(Message.error("权限被拒绝"))
    } else if (res.status >= 500) {
      Vue.use(Message.error("服务器开小差"))
    }
  }).catch(res => {
    console.log(res)
  })
}

export default {upload}
