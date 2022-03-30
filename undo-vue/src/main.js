import Vue from 'vue';
import App from './App';
import router from './router';
import localStore from "./components/js/LocalStore";
import {Message} from "element-ui";
import common from "./components/js/common";

Vue.prototype.$message = Message
Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  data() {
    return {
      user: common.User
    }
  },
  mounted() {
    localStore.loadUser()
  },
  watch: {
    user: {
      deep: true,
      handler: function (val) {
        localStore.storeUser()
      }
    }
  }
})
