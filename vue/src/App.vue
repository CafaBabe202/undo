<template>
  <div id="app">
      <MyAvatar v-show="login"/>
    <Me/>
    <LoginDialog/>
    <router-view/>
  </div>
</template>

<script>
import MyAvatar from "./components/MyAvatar";
import Me from "./components/Me"
import LoginDialog from "./components/LoginDialog";

import common from "./components/js/common";

export default {
  name: 'App',
  data() {
    return {
      login: true,
      drawer: true
    }
  }, methods: {
    open() {
      if (common.User.isLogin) {
        common.MeConfig.isDrawer = true
      } else {
        common.LoginDialogConfig.isDialog = true
      }
    }
  },
  components: {LoginDialog, MyAvatar, Me},
}
</script>
