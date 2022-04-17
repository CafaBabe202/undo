<template>
  <div class="file-box">
    <div class="file-list">
      <div class="file-item" v-for="file in this.file.fileList">
        <span class="file-name" v-show="file.id !== nowRenameFile.id" @dblclick="rename(file.id,file.name)">
          {{ file.name }}
        </span>
        <span class="file-name-input" v-show="file.id === nowRenameFile.id">
          <input v-model="nowRenameFile.name" @blur="submit" :id="file.id"
                 style="height: 100%;border: none;font-size: 25px;outline: none"/>
        </span>
        <span class="file-size">大小：{{ file.size }}</span>
        <span class="file-uploadTime">时间：{{ file.uploadTime }}</span>
        <div style="float: right;line-height: 50px;height: 100%;">
          <i class="el-icon-delete"/>
          <i class="el-icon-share"/>
        </div>
      </div>
    </div>
    <div class="file-upload-box" style="text-align: center">
      <el-upload
        action=""
        :show-file-list="false"
        :http-request="upload">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </div>
    <div class="loading">
      <div class="loading-status">
        <el-progress :text-inside="true" :stroke-width="26" :percentage="this.percentage"></el-progress>
      </div>
    </div>
  </div>
</template>

<script>
import fileUpload from "./js/fileUpload";
import common from "./js/common";

export default {
  name: "MyFile",
  data() {
    return {
      blockSize: 1024,
      file: common.File,
      nowRenameFile: common.nowRenameFile,
      percentage: 0,
    }
  }, methods: {
    rename(id, name) {
      setTimeout("document.getElementById(" + id + ").focus()", 50);
      this.nowRenameFile.id = id
      this.nowRenameFile.name = name
    }, submit() {
      this.nowRenameFile.id = ""
      this.nowRenameFile.name = ""
    },
    upload(file) {
      this.setUploadStatus(0)
      fileUpload.upload(file.file, (per) => {
        this.setUploadStatus(per)
      })
    },
    setUploadStatus(percentage) {
      this.percentage = percentage
      if (percentage === 100) {
        $(".loading").fadeOut()
      } else if (percentage < 100) {
        $(".loading").fadeIn()
      }
    }
  }, mounted() {
  }
}
</script>

<style scoped>
.file-box {
  width: 100%;
  margin-top: 10px;
  padding-top: 30px;
  padding-bottom: 30px;
  background-image: url("/static/bg.png");
}

.file-list {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px #bebebe;
  border: #b7b7b7 solid 1px;
  padding-top: 30px;
  padding-left: 10px;
  padding-right: 10px;
  margin-left: 20%;
  width: 60%;
}

.file-item {
  height: 50px;
  font-size: 30px;
  line-height: 50px;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
  border-radius: 5px;
  background-color: white;
  border: #d1d1d1 solid 1px;
  user-select: none;
  overflow: hidden;
}

.file-item i {
  margin-right: 15px;
}

.file-item i:hover {
  color: #409eff;
  cursor: pointer;
}

.file-uploadTime, .file-size {
  margin-left: 50px;
  font-size: 15px;
  color: #737373;
}

.file-upload-box {
  margin-top: 30px;
}

.loading {
  display: none;
  text-align: center;
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 10;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.7);
}

.loading-status {
  height: 200px;
  width: 500px;
  display: inline-block;
  margin-top: 200px;
  overflow: hidden;
}
</style>
