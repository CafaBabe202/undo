<template>
  <div>
    <div class="search">
      <div class="avatar">
        <MyAvatar/>
      </div>
      <div class="search_inner">
        <el-input placeholder="请输入内容" v-model="search.input" class="search_input" clearable/>
        <el-button class="search_button" type="primary" @click="doSearch">搜 索</el-button>
      </div>
    </div>
  </div>
</template>

<script>

import MyAvatar from "./MyAvatar";
import common from "./js/common";

export default {
  name: "AppHeader",
  components: {MyAvatar},
  data() {
    return {
      search: common.Searcher,
    }
  }, methods: {
    doSearch() {
      common.Index.visitRank = []
      window.location.href = "/search?title=" + this.search.input
    }
  }
}
</script>

<style>
.avatar {
  float: left;
  display: inline-block;
  padding-top: 10px;
  padding-left: 15px;
}

.search {
  height: 70px;
  background-color: #EFF0F1;
}

.search_inner {
  float: left;
  width: 40%;
  height: 100%;
  padding-top: 15px;
  margin-left: 30%;
}

.search_input {
  border-radius: 0;
  width: 70%;
  float: left;
}

.search_button {
  width: 20%;
  float: right;
}
</style>
