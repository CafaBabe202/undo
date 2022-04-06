<template>
  <!-- 头像按钮 -->
  <div>
    <div v-show="avatar.config.isLogin" @click="avatar.me.isDrawer=true">
      <img class="avatar_img" :src="user.avatar" alt="头像"/>
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
      user: common.User,
      avatar: {
        size: 50,
        bigSize: 100,
        config: common.User,
        me: common.MeConfig,
      }, dialog: common.LoginDialogConfig
    }
  }, methods: {}
}
</script>
<style>
.avatar_img {
  width: 50px;
  border-radius: 5px;
}
</style>
