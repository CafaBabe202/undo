<template>
  <!-- 左边的抽屉 -->
  <div class="demo-type">
    <el-drawer :size="drawer.size" :visible.sync="drawer.config.isDrawer" :direction="drawer.direction">

      <!-- 抽屉中最上边的头像 -->
      <div class="meAvatar">
        <el-avatar :size="avatar.bigSize" :src="user.avatar">
          <img :src="user.avatar" alt=""/>
        </el-avatar>
        <div class="username">
          <span>{{ user.username }}</span>
        </div>
      </div>

      <!-- 抽屉中的导航栏 -->
      <div class="drawerMenu">
        <el-menu :collapse="menu.collapse" class="el-menu-vertical-demo">
          <el-menu-item-group>
            <el-menu-item index="1">
              <i class="el-icon-s-home"/>
              <span slot="title" @click="toIndex">应用首页</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-user-solid"/>
              <span slot="title" @click="toUser">我的信息</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-document"></i>
              <span slot="title" @click="toArticle">我的文章</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-folder"></i>
              <span slot="title" @click="toFile">我的文件</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-switch-button"></i>
              <span slot="title" @click="exit">退出登录</span>
            </el-menu-item>
          </el-menu-item-group>
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
      user: common.User,
      avatar: {
        size: 50,
        bigSize: 100,
      }, drawer: {
        size: 300,
        config: common.MeConfig,
        direction: "ltr",
      }, menu: {
        collapse: false
      }
    }
  },
  methods: {
    toIndex() {
      window.location.href="/"
      // this.$router.push('/').catch(res => console.log(res));
      // common.MeConfig.isDrawer = false;
    }, toUser() {
      window.location.href="/user"
      // this.$router.push('/user').catch(res => console.log(res));
      // common.MeConfig.isDrawer = false;
    }, toArticle() {
      window.location.href="/myArticle"
      // this.$router.push('/myArticle').catch(res => console.log(res));
      // common.MeConfig.isDrawer = false;
    }, toFile() {
      window.location.href="/myFile"
      // this.$router.push('/myFile').catch(res => console.log(res));
      // common.MeConfig.isDrawer = false;
    }, exit() {
      common.User.reset()
      this.toIndex()
    }
  }
}
</script>
<style>
.demo-type {
  user-select: none;
}

.meAvatar {
  width: 100%;
  text-align: center;
}

.meAvatar > img {
  cursor: pointer;
  display: inline-block;
}

.username {
  padding-top: 20px;
  color: darkgray;
  cursor: pointer;
  user-select: none;
}

.drawerMenu {
  margin-top: 30px;
}
</style>
