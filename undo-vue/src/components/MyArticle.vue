<template>
  <div class="myArticle-box">
    <!-- 左边的分类卡片 -->
    <div class="myArticle-summary">
      <el-card class="myArticle-summary-clazzs" shadow="hover">
        <div slot="header" class="clearfix">
          <span>所有分类</span>
          <el-button style="float: right; padding: 3px 0" type="text">添加分类</el-button>
        </div>
        <div v-for="o in this.article.allClazz" :key="o.id" class="myArticle-clazzs-item">
          <i class="el-icon-collection"/>
          <span>{{ o.name }}</span>
        </div>
      </el-card>

      <el-descriptions class="myArticle-summary-statistics" title="" :column="1" size="mini" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-collection"></i>
            分类数
          </template>
          10
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-document"></i>
            文章数
          </template>
          10
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-view"></i>
            浏览量
          </template>
          100
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-lollipop"></i>
            喜欢数
          </template>
          1000
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <!-- 中间的文章 -->
    <div class="myArticle-show-article">
      <div v-for="article in this.article.articles" class="myArticle-article-card">
        <p class="myArticle-article-card-title">
          <span>{{ article.title }}</span>
          <i class="el-icon-edit"/>
          <i class="el-icon-share"/>
          <i class="el-icon-sunny" v-show="article.public"/>
          <i class="el-icon-cloudy" v-show="!article.public"/>
        </p>
        <p class="myArticle-article-card-summary">{{ article.summary }}</p>
        <ul>
          <li><i class="el-icon-lollipop"/>点赞：{{ article.like }}</li>
          <li><i class="el-icon-view"/>浏览量：{{ article.visit }}</li>
          <li><i class="el-icon-refresh-right"/>更新时间：{{ article.updateTime }}</li>
          <li><i class="el-icon-collection"/>分类：{{ article.clazzName }}</li>
        </ul>
      </div>
    </div>

    <!-- clear float -->
    <div style="clear: both"></div>
  </div>
</template>

<script>
import common from "./js/common";

export default {
  name: "MyArticle",
  data() {
    return {
      article:common.UserArticle
    }
  }, methods: {}, mounted() {
  }, watch: {
  }
}
</script>

<style scoped>
.myArticle-box {
  width: 100%;
  margin-top: 10px;
  background-image: url("/static/bg.png");
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
  border: rgb(222, 222, 222) solid 2px;
}

.myArticle-article-card:hover {
  transition: 0.5s;
  background-color: #f9f9f9;
  box-shadow: 3px 3px 10px #bebebe;
}

.myArticle-article-card-title {
  margin: 0;
  padding-top: 7px;
  padding-bottom: 7px;
  padding-left: 10px;
  font-size: 20px;
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
  border-top: rgb(222, 222, 222) solid 1px;
  border-bottom: rgb(222, 222, 222) solid 2px;
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
  margin-left: 5px;
  margin-bottom: 10px;
}
</style>
