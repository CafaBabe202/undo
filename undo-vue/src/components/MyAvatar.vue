<template>
  <!-- 头像按钮 -->
  <div class="avatar">
    <div v-show="avatar.config.isLogin" @click="avatar.me.isDrawer=true">
      <el-avatar :size="avatar.size" :src="avatar.config.avatarUrl" @error="errorHandler">
        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" alt=""/>
      </el-avatar>
    </div>
    <el-button-group v-show="!avatar.config.isLogin">
      <el-button type="primary" @click="dialog.isDialog=true;dialog.isLogin=true" size="small" plain>登 录</el-button>
      <el-button type="primary" @click="dialog.isDialog=true;dialog.isLogin=false" size="small" plain>注 册</el-button>
    </el-button-group>
  </div>
</template>

<script>

import common from "./js/common";

export default {
  name: "MyAvatar",
  data() {
    return {
      avatar: {
        size: 50,
        bigSize: 100,
        config: common.User,
        me: common.MeConfig,
      }, dialog: common.LoginDialogConfig
    }
  }, methods: {
    errorHandler() {
      return true
    }
  }
}
</script>

<style>

.avatar {
  position: absolute;
  margin-top: 20px;
  margin-left: 30px;
}
</style>
