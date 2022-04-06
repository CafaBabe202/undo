<template>
  <div id="app">
    <div class="header">
      <Search/>
      <Me/>
    </div>
    <div class="body">
      <LoginDialog/>
      <router-view/>
    </div>
    <div>
      <AppFoot/>
    </div>
    <div>
    </div>
  </div>
</template>

<script>
import Me from "./components/Me"
import LoginDialog from "./components/LoginDialog";
import Search from "./components/AppHeader";
import AppFoot from "./components/AppFoot";

export default {
  name: 'App',
  data() {
    return {
      login: true,
      drawer: true
    }
  }, methods: {
  },
  components: {AppFoot, Search, LoginDialog, Me},
}
</script>
<style>
</style>
