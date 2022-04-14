import axios from "axios";
import SparkMD5 from 'spark-md5'

const apis = {
  md5CheckAndAdd: "/fileApi/upload/md5Check"
}

const md5CheckAndAdd = function (file) {
  let fileReader = new FileReader()
  let Spark = new SparkMD5.ArrayBuffer()
  fileReader.readAsArrayBuffer(file)
  fileReader.onload = function (e) {
    Spark.append(e.target.result)
    let md5 = Spark.end()
    console.log(md5)
    axios.post(apis.md5CheckAndAdd, {"md5": md5, fileName: file.name}, null).then(data => {
      console.log(data.data)
    })
  }
}

export default {md5CheckAndAdd}
