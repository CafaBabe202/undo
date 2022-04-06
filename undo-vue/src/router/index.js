import Vue from 'vue'
import Router from 'vue-router'

import Index from '../components/Index'
import User from '../components/User'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index
    }, {
      path: "/user",
      name: "user",
      component: User
    }
  ]
})
