<template>
  <div class="myArticle-box">
    <!-- 左边的分类卡片 -->
    <div class="myArticle-summary">
      <el-card class="myArticle-summary-clazzs" shadow="hover">
        <div slot="header" class="clearfix">
          <span>所有分类</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="addClazz">添加分类</el-button>
        </div>
        <div v-for="o in this.article.allClazz" :key="o.id" @click="toClazz(o.id)"
             @dblclick="editClazzName(o.id,o.name)"
             class="myArticle-clazzs-item">
          <i class="el-icon-collection i-color"/>
          <span>{{ o.name }}</span>
        </div>
      </el-card>

      <el-descriptions class="myArticle-summary-statistics" title="" :column="1" size="mini" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-collection i-color"></i>分类数
          </template>
          {{ this.article.statistics.clazzNum }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-document i-color"></i>文章数
          </template>
          {{ this.article.statistics.number }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-view i-color"></i>浏览量
          </template>
          {{ this.article.statistics.visit }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-lollipop i-color"></i>喜欢数
          </template>
          {{ this.article.statistics.like }}
        </el-descriptions-item>
      </el-descriptions>

      <el-button class="myArticle-summary-add" type="primary" @click="addArticle">
        <i class="el-icon-edit"/>
        添加文章
      </el-button>

    </div>

    <!-- 中间的文章 -->
    <div class="myArticle-show-article">
      <div v-for="article in this.article.articles" class="myArticle-article-card">
        <p class="myArticle-article-card-title">
          <span @click="showArticle(article.id)">{{ article.title }}</span>
          <i class="el-icon-edit" @click="editArticle(article.id)"/>
          <i class="el-icon-delete" @click="deleteArticle(article.id)"/>
          <i class="el-icon-share" @click="shareArticle(article.id)"/>
          <span @click="changePrivate(article.id)">
          <i class="el-icon-sunny" v-show="!article.private"/>
          <i class="el-icon-cloudy" v-show="article.private"/>
          </span>
        </p>
        <p class="myArticle-article-card-summary">{{ article.summary }}</p>
        <ul>
          <li><i class="el-icon-lollipop i-color"/>点赞：{{ article.like }}</li>
          <li><i class="el-icon-view i-color"/>浏览量：{{ article.visit }}</li>
          <li><i class="el-icon-collection i-color"/>分类：{{ article.clazzName }}</li>
          <li><i class="el-icon-refresh-right i-color"/>更新时间：{{ article.updateTime.substr(0,10) }}</li>
        </ul>
      </div>
    </div>

    <!-- clear float -->
    <div style="clear: both"></div>

    <!-- 修改分类信息的 Dialog -->
    <div>
      <el-dialog title="修改分类信息" :visible.sync="editClazzNameDialog" width="30%" center>
        <span><input v-model="nowEditClazz.newVal" class="myArticle-input-clazz-name"/></span>
        <span slot="footer" class="dialog-footer">
          <template>
            <el-popconfirm title="你确定删除该分类吗？" @confirm="deleteClazz">
              <el-button slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
          <el-button style="margin-left: 50px" type="primary" @click="renameClazz">确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import common from "./js/common";
import ajax from "./js/ajax";
import Vue from "vue";
import VueClipboard from 'vue-clipboard2';
import {Message} from "element-ui";

Vue.use(VueClipboard)

export default {
  name: "MyArticle",
  data() {
    return {
      user: common.User,
      article: common.UserArticle,
      editClazzNameDialog: false,
      nowEditClazz: common.ClazzNowEdit
    }
  }, methods: {
    changePrivate(id) {
      ajax.changeArticlePrivate(id)
    }, addClazz() {
      ajax.addClazz()
    }, toClazz(id) {
      if (common.UserArticle.nowClazzId !== id) {
        ajax.getArticles(id)
      }
    }, editClazzName(id, name) {
      this.nowEditClazz.id = id
      this.nowEditClazz.newVal = name
      this.editClazzNameDialog = true
    }, deleteClazz() {
      ajax.deleteClazz()
      this.editClazzNameDialog = false
    }, renameClazz() {
      ajax.renameClazz()
      this.editClazzNameDialog = false;
    }, shareArticle(id) {
      this.$copyText("http://undo.vip:8080/showArticle/" + id)
      Vue.use(Message.success("文章链接已添加到剪切板"))
    }, addArticle() {
      common.ArticleNowEdit.reset()
      this.$router.push("/editArticle").catch(res => console.log(res))
    }, deleteArticle(id) {
      this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ajax.deleteArticle(id)
      });
    }, editArticle(id) {
      this.$router.push("/editArticle/" + id).catch(data => console.log(data))
    }, showArticle(id) {
      this.$router.push("/showArticle/" + id).catch(data => console.log(data))
    },

  }, mounted() {
    setTimeout(ajax.refreshArticle, 50)
  }, watch: {}
}
</script>

<style scoped>
i {
  margin-right: 10px;
}

i:hover, .i-color {
  color: rgba(64, 158, 255, 1);
}

.myArticle-box {
  width: 100%;
  margin-top: 10px;
  background-image: url("/static/bg.png");
}

.myArticle-input-clazz-name {
  width: 60%;
  height: 30px;
  font-size: 15px;
  border: black solid 1px;
  border-radius: 5px;
  outline: none;
  margin-left: 20%;
  box-shadow: #ececec 0 0 10px;
}

.myArticle-summary {
  width: 17%;
  float: left;
  margin-top: 30px;
  margin-left: 10%;
  margin-bottom: 30px;
  user-select: none;
}

.myArticle-summary-statistics {
  margin-top: 30px;
  border-radius: 5px;
  box-shadow: #d5d5d5 0 0 10px;
}

.myArticle-summary-add {
  width: 100%;
  margin-top: 30px;
}

.myArticle-show-article {
  width: 50%;
  float: left;
  margin-left: 20px;
  margin-top: 30px;
}

.myArticle-article-card {
  background-color: white;
  margin-bottom: 30px;
  border-radius: 5px;
  border: rgba(198, 226, 255, 1) solid 2px;
}

.myArticle-article-card:hover {
  transition: 0.5s;
  background-color: #f9f9f9;
  box-shadow: 3px 3px 10px rgba(198, 226, 255, 1);
}

.myArticle-article-card-title {
  margin: 0;
  padding-top: 7px;
  padding-bottom: 7px;
  padding-left: 10px;
  font-size: 20px;
  cursor: pointer;
}

.myArticle-article-card-title i {
  float: right;
  cursor: pointer;
  margin-right: 15px;
}

.myArticle-article-card-summary {
  margin: 0;
  padding: 10px;
  font-size: 13px;
  text-indent: 30px;
  letter-spacing: 1px;
  line-height: 25px;
  border-top: rgba(198, 226, 255, 1) solid 2px;
  border-bottom: rgba(198, 226, 255, 1) solid 2px;
}

.myArticle-article-card ul {
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 0;
  list-style: none;
  margin: 0;
  user-select: none;
}

.myArticle-article-card li {
  margin-left: 40px;
  font-size: 13px;
  color: #606266;
  display: inline-block;
}

.myArticle-clazzs-item span {
  display: inline-block;
  cursor: pointer;
  font-size: 14px;
  margin-bottom: 10px;
}
</style>
