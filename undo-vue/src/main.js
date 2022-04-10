import Vue from 'vue';
import App from './App';
import router from './router';
import localStore from "./components/js/LocalStore";
import {Message} from "element-ui";
import common from "./components/js/common";
import ajax from "./components/js/ajax";

Vue.prototype.$message = Message
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  data() {
    return {
      user: common.User,
    }
  }, methods: {}, mounted() {
    localStore.loadUser()
    ajax.refreshUser()
  }, watch: {
    user: {
      deep: true,
      handler: function (val) {
        localStore.storeUser()
        if (!common.User.isLogin) {
          this.$router.push("/").catch(res => console.log(res))
        }
      }
    }
  }
})
