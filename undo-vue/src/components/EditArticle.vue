<template>
  <div class="edit-box">
    <div class="editor-box">
      <input v-model="nowEdit.title" type="text" class="editor-title" maxlength="32"/>
      <select v-model="nowEdit.clazzId" class="editor-clazz">
        <option value="" selected disabled hidden>请选择一个分类</option>
        <option v-for="c in article.allClazz" :value="c.id">
          {{ c.name }}
        </option>
      </select>
      <textarea v-model="nowEdit.summary" class="editor-summary" maxlength="256"/>
      <mavon-editor class="editor" v-model="nowEdit.content" :ishljs="true" @save="submitArticle"/>
      <el-button type="primary" class="editor-save" @click="submitArticle">保存</el-button>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import common from "./js/common";
import ajax from "./js/ajax";

Vue.use(mavonEditor)
export default {
  name: "EditArticle",
  data() {
    return {
      article: common.UserArticle,
      nowEdit: common.ArticleNowEdit,
      value: ""
    }
  }, methods: {
    submitArticle() {
      ajax.editArticle()
    }
  }, mounted() {
    setTimeout(ajax.getArticleClazz, 50)
    this.nowEdit.id = this.$route.params.id
  }
}
</script>

<style scoped>
.edit-box {
  width: 100%;
  margin-top: 10px;
  padding-top: 30px;
  padding-bottom: 30px;
  background-image: url("/static/bg.png");
}

.editor-box {
  width: 80%;
  margin-left: 10%;
}

.editor-title {
  width: 60%;
  font-size: 20px;
  float: left;
  padding-left: 15px;
}

.editor-clazz {
  width: 35%;
  float: right;
  font-size: 15px;
  background-color: white;
}

.editor-title, .editor-clazz {
  height: 40px;
  outline: none;
  border-radius: 5px;
  border: 0;
}


.editor-summary {
  width: 99%;
  height: 80px;
  outline: none;
  border: #bebebe;
  border-radius: 5px;
  font-size: 20px;
  line-height: 30px;
  resize: vertical;
  margin-top: 30px;
  padding: 10px;
  clear: both;
}

.editor {
  width: 100%;
  margin-top: 30px;
}

.editor-save {
  width: 10%;
  margin-left: 45%;
  margin-top: 30px;
}
</style>
