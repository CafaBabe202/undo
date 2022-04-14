<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="index-box">
    <!--用户排行榜-->
    <div class="index-user-rank">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span><b>用户排名榜</b></span>
        </div>
        <div v-for="u in this.index.userRank" :key="u.id" class="index-user-rank-card">
          <el-avatar :src="u.avatar" class="user-card-avatar" :size="60" shape="square"></el-avatar>
          <span class="index-user-rank-card-title"><b>{{ u.username }}</b></span><br/>
          <span class="index-user-rank-card-label"><i class="el-icon-document i-color"/>&nbsp;{{ u.number }}</span>
          <span class="index-user-rank-card-label"><i class="el-icon-lollipop i-color"/>&nbsp;{{ u.like }}</span>
          <span class="index-user-rank-card-label"><i class="el-icon-view i-color"/>&nbsp;{{ u.visit }}</span>
        </div>
      </el-card>
    </div>

    <!-- 中间的排行榜 -->
    <div class="index-visit-rank">
      <div v-for="article in this.index.visitRank" class="index-article-card">
        <p class="index-article-card-title">
          <span @click="showArticle(article.id)">{{ article.title }}</span>
          <i class="el-icon-share" @click="shareArticle(article.id)"/>
        </p>
        <p class="index-article-card-summary">{{ article.summary }}</p>
        <ul>
          <li><i class="el-icon-lollipop i-color"/>点赞：{{ article.like }}</li>
          <li><i class="el-icon-view i-color"/>浏览量：{{ article.visit }}</li>
          <li><i class="el-icon-collection i-color"/>分类：{{ article.clazzName }}</li>
          <li><i class="el-icon-refresh-right i-color"/>更新时间：{{ article.updateTime.substr(0, 10) }}</li>
        </ul>
      </div>
      <div v-show="this.$route.path==='/search'"
           style="width: 100%;height: 30px;line-height: 30px;text-align: center;color: rgb(64, 158, 255);cursor: pointer;user-select: none">
        <span v-show="this.search.hasMore" @click="more">点击加载更多</span>
        <span v-show="!this.search.hasMore">没有更多了</span>
      </div>
    </div>

    <div style="clear:both;"></div>
  </div>
</template>

<script>
import ajax from "./js/ajax";
import common from "./js/common";
import Vue from "vue";
import {Message} from "element-ui";

export default {
  name: 'Index',
  data() {
    return {
      index: common.Index,
      search: common.Searcher,
    }
  },
  methods: {
    showArticle(id) {
      this.search.reading = true
      this.$router.push("/showArticle/" + id).catch(data => console.log(data))
    }, shareArticle(id) {
      this.$copyText("http://127.0.0.1:8080/showArticle/" + id)
      Vue.use(Message.success("文章链接已添加到剪切板"))
    }, doSearch(research) {
      if (!this.search.reading) {
        if (research)
          this.search.nowPage = 1
        this.search.input = this.$route.query.title
        ajax.search(this.$route.query.title, this.search.nowPage)
        this.search.nowPage += 1
      }
    }, more() {
      this.search.reading = false
      this.doSearch(false)
    }
  }, mounted() {
    ajax.getUserRank()
    if (this.$route.path === "/") {
      ajax.getVisitRank()
    } else if (this.$route.path === "/search") {
      this.doSearch(true)
    }
  }, watch: {
    $route: {
      deep: true,
      handler() {
        this.doSearch(true)
      }
    }
  }
}
</script>
<style>
.index-box {
  width: 100%;
  margin-top: 10px;
  padding-top: 10px;
  padding-bottom: 30px;
  background-image: url("/static/bg.png");
}

.index-user-rank {
  width: 20%;
  float: left;
  margin-left: 10%;
}

.index-user-rank-card {
  padding-top: 20px;
  padding-bottom: 20px;
}

.user-card-avatar {
  float: left;
  margin-left: 10px;
  margin-right: 10px;
}

.index-visit-rank {
  width: 50%;
  float: left;
  margin-left: 30px;
}

.index-article-card {
  background-color: white;
  margin-bottom: 30px;
  border-radius: 5px;
  border: rgba(198, 226, 255, 1) solid 2px;
}

.i-color {
  color: rgba(64, 158, 255, 1);
}

.index-user-rank-card-title {
  display: inline-block;
  font-size: 17px;
}

.index-user-rank-card-label {
  display: inline-block;
  margin-top: 15px;
  margin-right: 15px;
}

.index-article-card:hover {
  transition: 0.5s;
  background-color: #f9f9f9;
  box-shadow: 3px 3px 10px rgba(198, 226, 255, 1);
}

.index-article-card-title {
  margin: 0;
  padding-top: 7px;
  padding-bottom: 7px;
  padding-left: 10px;
  font-size: 20px;
  cursor: pointer;
}

.index-article-card-title i {
  float: right;
  cursor: pointer;
  margin-right: 15px;
}

.index-article-card-summary {
  margin: 0;
  padding: 10px;
  font-size: 13px;
  text-indent: 30px;
  letter-spacing: 1px;
  line-height: 25px;
  border-top: rgba(198, 226, 255, 1) solid 2px;
  border-bottom: rgba(198, 226, 255, 1) solid 2px;
}

.index-article-card ul {
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 0;
  list-style: none;
  margin: 0;
  user-select: none;
}

.index-article-card li {
  margin-left: 40px;
  font-size: 13px;
  color: #606266;
  display: inline-block;
}
</style>
