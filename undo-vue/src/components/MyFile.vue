<template>
  <el-upload
    drag
    action="http://127.0.0.1:8080/fileApi/upload"
    :http-request="upload"
    multiple>
    <i class="el-icon-upload"></i>
  </el-upload>
</template>

<script>
import fileUpload from "./js/fileUpload";

export default {
  name: "MyFile",
  data() {
    return {
      blockSize: 1024,
      fileList: []
    }
  },
  methods: {
    upload(file) {
      fileUpload.md5CheckAndAdd(file.file)
    }
  }
}
</script>

<style scoped>

</style>
