import Vue from 'vue'
import Router from 'vue-router'

import Index from '../components/Index'
import User from '../components/User'
import MyArticle from '../components/MyArticle'
import ShowArticle from '../components/ShowArticle'
import MyFile from '../components/MyFile'
import EditArticle from '../components/EditArticle'

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
    }, {
      path: "/myArticle",
      name: "myArticle",
      component: MyArticle
    }, {
      path: "/showArticle/:id/",
      name: "showArticle",
      component: ShowArticle
    }, {
      path: "/myFile",
      name: "myFile",
      component: MyFile
    }, {
      path: "/editArticle/:id/",
      name: "editArticle",
      component: EditArticle
    },{
      path: "/editArticle",
      name: "editArticle",
      component: EditArticle
    }
  ]
})
