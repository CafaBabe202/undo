<template>
  <div class="edit-box">
    <div class="editor-box">
      <input placeholder="请输入标题" v-model="nowEdit.title" type="text" class="editor-title" maxlength="32"/>
      <select v-model="nowEdit.clazzId" class="editor-clazz">
        <option value="" selected disabled hidden>请选择一个分类</option>
        <option v-for="c in article.allClazz" :value="c.id">
          {{ c.name }}
        </option>
      </select>
      <textarea v-model="nowEdit.summary" class="editor-summary" maxlength="256" placeholder="请输入摘要"/>
      <textarea v-model="nowEdit.updateSummary" placeholder="请输入更新日志" class="editor-update-summary" v-show="nowEdit.id>0"
                maxlength="256"/>
      <mavon-editor class="editor" v-model="nowEdit.content" :toolbars="toolbars" :ishljs="true" @imgAdd="imgAdd"
                    @save="submitArticle"/>
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
      value: "",
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: false, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      }
    }
  }, methods: {
    submitArticle() {
      ajax.editArticle()
    }, getArticleForEdit() {
      ajax.getArticleForEdit(this.nowEdit.id)
    }, imgAdd(file, a) {
      console.log(file, a)
    }
  }, mounted() {
    this.nowEdit.id = this.$route.params.id
    setTimeout(ajax.getArticleClazz, 50)
    if (this.nowEdit.id !== undefined)
      setTimeout(this.getArticleForEdit, 50)
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


.editor-summary, .editor-update-summary {
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
