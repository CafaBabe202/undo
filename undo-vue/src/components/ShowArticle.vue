<template>
  <div class="showArticle-box">

    <!--左边的目录-->
    <div class="showArticle-box-summary ">
      <div class="showArticle-box-summary-title"><b>目录</b></div>
    </div>

    <!--中间的正文-->
    <mavon-editor
      class="article-content"
      :value="article.content"
      :editable="false"
      :toolbarsFlag="false"
      :subfield="false"
      :ishljs="true"
      @change="createSummary"
      defaultOpen="preview"/>

    <!--右边的更新记录-->
    <div class="showArticle-box-records">
      <!--用户信息-->
      <div class="showArticle-box-records-user" v-show="this.article.userId !== nowUser.id">
        <el-avatar style="float: left;margin-left: 20px;" shape="square" :size="80"
                   :src="this.article.user.avatar"></el-avatar>
        <div
          style="float: left;display: block;margin-left: 10px;font-size: 20px;height: 50px;line-height: 50px;width: 100px;">
          {{ this.article.user.username }}
        </div>
        <div
          style="float: left;display: block;height: 50px;line-height: 50px;width: 100px;text-align: center">
          <i class="el-icon-star-on" v-show="!this.good"
             style="color:#d7d7d7;user-select: none;cursor: pointer;font-size: 30px;"
             @click="onGood"/>
          <i class="el-icon-star-on" v-show="this.good"
             style="color: rgb(245, 108, 108);user-select: none;cursor: pointer;font-size: 30px;"/>
        </div>
        <div
          style="float: left;display: block;margin-left: 10px;font-size: 15px;height: 30px;line-height: 30px;width: 200px;">
          {{ this.article.user.sign }}
        </div>
      </div>

      <!--更新记录-->
      <el-timeline>
        <el-timeline-item v-for="r in article.records" style="user-select: none"
                          :timestamp="r.timestamp" :color="r.color" placement="top">
          <p style="font-size: 10px;padding: 3px;cursor: pointer" @click="toVersion(r.versionId)">{{ r.summary }}</p>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div style="clear: both"></div>
    <div class="showArticle-box-top" @click="toTop">Top</div>
  </div>

</template>

<script>
import common from "./js/common";
import ajax from "./js/ajax";
import Vue from "vue";
import {Message} from "element-ui";
import $ from 'jquery'

export default {
  name: "ShowArticle",
  data() {
    return {
      nowUser: common.User,
      id: this.$route.params.id,
      article: common.ArticleNowShow,
      good: false
    }
  }, methods: {
    createSummary() {
      setTimeout(function () {
        $(".showArticle-box-summary h5").remove()
        $(".article-content").find(":header").each(function () {
          let w = $(this).offset().top
          if (w !== 0) {
            $("<h5>" + $(this).html() + "</h5>").click(function () {
              $("html,body").animate({scrollTop: w}, 1000);
            }).css({
              "user-select": "none",
              "cursor": "pointer"
            }).appendTo(".showArticle-box-summary")
          }
        })
      }, 100)
    }, toTop() {
      $("html,body").animate({scrollTop: 0}, 1000);
    }, onGood() {
      this.good = true
      ajax.likeArticle(this.article.id)
    }, toVersion(id) {
      ajax.getArticleForShow(this.id, id)
    }, refresh() {
      ajax.getArticleRecords(this.id, (data) => {
        if (data.length === 0) {
          Vue.use(Message.error("该文章我没有被审核"))
          this.$router.back()
          return
        }
        ajax.getArticleForShow(this.id, null)
      })
    }

  }, mounted() {
    common.ArticleNowShow.reset()
    setTimeout(this.refresh, 100)
  }
}
</script>

<style scoped>
.showArticle-box {
  width: 100%;
  margin-top: 10px;
  padding-top: 20px;
  padding-bottom: 30px;
  background-image: url("/static/bg.png");
}

table {
  width: 100%;
  border-collapse: collapse;
}

table td {
  display: inline-block;
  margin-top: 30px;
  vertical-align: text-top;
}

.showArticle-box-summary {
  width: 17%;
  float: left;
  margin-left: 10px;
  padding-left: 20px;
  box-shadow: #d5d5d5 0 0 10px;
  background-color: white;
}

.showArticle-box-summary-title {
  height: 50px;
  line-height: 50px;
  font-size: 20px;
}


.article-content {
  width: 50%;
  float: left;
  margin-left: 20px;
}

.showArticle-box-records {
  width: 23%;
  float: left;
  padding-right: 20px;
  padding-top: 20px;
  margin-left: 20px;
  box-shadow: #d5d5d5 0 0 10px;
  background-color: white;
}

.showArticle-box-records-user {
  height: 130px;
  width: 100%;
}

.showArticle-box-top {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  text-align: center;
  line-height: 40px;
  cursor: pointer;
  user-select: none;
  background-color: #8cc5ff;
  bottom: 50px;
  right: 50px;
  position: fixed;
}

.showArticle-box-top:hover {
  box-shadow: #409eff 0 0 3px;
}
</style>
