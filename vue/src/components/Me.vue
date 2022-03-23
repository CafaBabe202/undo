<template>
  <!-- 左边的抽屉 -->
  <div class="demo-type">
    <el-drawer :size="drawer.size" :visible.sync="drawer.config.isDrawer" :direction="drawer.direction">

      <!-- 抽屉中最上边的头像 -->
      <div class="meAvatar">
        <el-avatar :size="avatar.bigSize" :src="avatar.config.avatarUrl" @error="errorHandler">
          <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" alt=""/>
        </el-avatar>
      </div>

      <!-- 抽屉中的导航栏 -->
      <div class="drawerMenu">
        <el-menu class="el-menu-vertical-demo" @open="" @close="" :collapse="menu.collapse">
          <el-menu-item index="1">
            <i class="el-icon-user-solid"/>
            <span slot="title">我的信息</span>
          </el-menu-item>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">我的文章</span>
            </template>
            <el-submenu v-for="book in menu.books" :index="book">
              <span slot="title">{{ book }}</span>
              <el-menu-item v-for="art in menu.article[book]" :index="book+art">{{ art }}</el-menu-item>
            </el-submenu>

          </el-submenu>

          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>
      </div>

    </el-drawer>
  </div>
</template>
<script>
import Vue from "vue";
import common from "./js/common";

import ElementUIComponent from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUIComponent)
export default {
  data() {
    return {
      avatar: {
        size: 50,
        bigSize: 100,
        config: common.User
      }, drawer: {
        size: 300,
        config: common.MeConfig,
        direction: "ltr",
      }, menu: {
        collapse: false,
        books: ["Java", "C++"],
        article: {
          "Java": ["Java 1", "Java 2"],
          "C++": ["C++ 1", "C++ 2"]
        }
      }
    }
  },
  methods: {
    errorHandler() {
      return true
    },
  }
}
</script>
<style>
.meAvatar {
  width: 100%;
  text-align: center;
}

.meAvatar > img {
  display: inline-block;
}

.drawerMenu {
  margin-top: 30px;
}
</style>
