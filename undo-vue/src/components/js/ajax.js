import axios from 'axios';
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";

/**
 * 所有的 Api 接口地址
 * @type {{changeArticlePrivate: string, registerUrl: string, uploadAvatar: string, getMyArticleClazz: string, loginUrl: string, getMyArticles: string, getMyDetailUrl: string, getMyArticleStatistics: string, updateUser: string}}
 */
const apis = {
  registerUrl: "/userApi/user/register",
  loginUrl: "/userApi/user/login",
  getMyDetailUrl: "/userApi/user/getMyDetail.token",
  updateUser: "/userApi/user/set.token",
  uploadAvatar: "/userApi/avatar/uploadAvatar.token",

  editArticle: "/articleApi/article/editArticle.token",
  deleteArticle: "/articleApi/article/deleteArticle.token",
  getMyArticleClazz: "/articleApi/clazz/getAllClazz",
  getMyArticleStatistics: "/articleApi/article/getStatistics.token",
  getMyArticles: "/articleApi/article/getArticles.token",
  changeArticlePrivate: "/articleApi/article/changePrivate.token",

  addClazz: "/articleApi/clazz/add.token",
  deleteClazz: "/articleApi/clazz/delete.token",
  renameClazz: "/articleApi/clazz/rename.token",
}

//获取用户信息的方法
const getMyDetail = function () {
  GET(apis.getMyDetailUrl, null, data => {
    for (let f in data) {
      common.User[f] = data[f]
    }
    getArticleStatistics()
  })
}

//注册接口
const register = function () {
  POST(apis.registerUrl, common.RegisterForm, data => {
    common.LoginForm.email = common.RegisterForm.email
    common.LoginForm.password = common.RegisterForm.password
    login()
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 登录
const login = function () {
  POST(apis.loginUrl, common.LoginForm, data => {
    Vue.use(Message.success("登录成功！"))
    common.LoginDialogConfig.isDialog = false
    common.User.token = data
    common.User.isLogin = true
    common.LoginForm.reset()
    getMyDetail()
  }, data => {
    Vue.use(Message.error("登录失败！"))
  })
}

// 更新用户信息
const update = function () {
  POST(apis.updateUser, common.UserNowEdit, data => {
    Vue.use(Message.success("设置成功"))
  }, null)
}

// 设置头像
const uploadAvatar = function (file) {
  if (file.size / 1024 / 1024 > 2) {
    Vue.use(Message.error("上传头像图片大小不能超过 2MB!"))
    return
  }
  let data = new FormData()
  data.append('file', file)
  axios.post(
    apis.uploadAvatar, data, {
      headers: {
        "Content-Type": "multipart/form-data; boundary=----WebKitFormBoundaryfMpiwYVb3Eliuo7t",
        token: common.User.token
      }
    }
  ).then(res => {
    res = res.data
    if (res.status === 200) {
      Vue.use(Message.success(res.data))
      getMyDetail()
    } else if (res.status === 401) {
      Vue.use(Message.error("Token失效请重新登录！"))
      common.User.reset()
    }
  }).catch(res => {
    Vue.use(Message.error("网络错误！"))
  })
}

// 获取用户文章统计信息
const getArticleStatistics = function () {
  GET(apis.getMyArticleStatistics, null, res => {
    common.UserArticle.statistics = res
  })
}

const editArticle = function () {
  if (common.ArticleNowEdit.id === undefined || common.ArticleNowEdit.id === "") {
    common.ArticleNowEdit.id = -1
    common.ArticleNowEdit.isPrivate = false
  }
  POST(apis.editArticle, common.ArticleNowEdit, data => {
    Vue.use(Message.success(data))
    common.ArticleNowEdit.reset()
    window.location.href = "/myArticle";
  }, data => {
    Vue.use(Message.error(data))
  })
}

const deleteArticle = function (id) {
  POST(apis.deleteArticle + "/" + id, null, data => {
    Vue.use(Message.success(data))
    getArticles(common.UserArticle.nowClazzId)
  }, data => {
    Vue.use((Message.error(data)))
  })
}

// 获取用户文章分类列表
const getArticleClazz = function (refreshArticle) {
  GET(apis.getMyArticleClazz + "/" + common.User.id, null, res => {
    common.UserArticle.allClazz = res
    if (refreshArticle)
      getArticles(common.UserArticle.allClazz[0].id)
  })
}

// 获取自己的某个分类的所有分类，如果 clazzId 为 -1 ，获取所有文章
const getArticles = function (clazzId) {
  GET(apis.getMyArticles + "/" + clazzId, null, res => {
    common.UserArticle.articles = res
  }, null)
}

// 改变文章的访问权限
const changeArticlePrivate = function (id) {
  POST(apis.changeArticlePrivate + "/" + id, null, data => {
    for (let a in common.UserArticle.articles)
      if (common.UserArticle.articles[a].id === id)
        common.UserArticle.articles[a].private = !common.UserArticle.articles[a].private
  }, null)
}

const addClazz = function () {
  POST(apis.addClazz, {name: "未命名"}, data => {
    Vue.use(Message.success("添加成功"))
    getArticleClazz(false)
    getArticleStatistics()
  }, data => {
    Vue.use(Message.error("添加失败"))
  })
}

const deleteClazz = function () {
  POST(apis.deleteClazz + "/" + common.ClazzNowEdit.id, null, data => {
    Vue.use(Message.success(data))
    getArticleClazz(true)
    getArticleStatistics()
  }, data => {
    Vue.use(Message.error(data))
  })
}

const renameClazz = function () {
  POST(apis.renameClazz, common.ClazzNowEdit, data => {
    Vue.use(Message.success(data))
    getArticleClazz(true)
  }, data => {
    Vue.use(Message.error(data))
  })
}

const refreshArticle = function () {
  getArticleStatistics()
  getArticleClazz(true)
}

const refreshUser = function () {
  getMyDetail()
}

const GET = function (url, data, ok, error) {
  axios.get(
    url, {
      params: data,
      headers: {
        token: common.User.token
      }
    }
  ).then(res => {
    res = res.data
    if (res.status === 200 && ok !== null) {
      ok(res.data);
    } else if (res.data === 400) {
      if (error !== null)
        error(res)
    } else if (res.status === 401) {
      Vue.use(Message.error("Token 失效，请重新登录"))
      common.User.reset()
    } else if (res.status >= 500) {
      Vue.use(Message.error("服务器开小差"))
    }
  }).catch(res => {
    console.log(res)
  })
}

const POST = function (url, data, ok, error) {
  axios.post(url, data, {
      headers: {token: common.User.token}
    }
  ).then(res => {
    res = res.data
    if (res.status === 200) {
      if (ok !== null)
        ok(res.data)
    } else if (res.status === 400) {
      if (error !== null)
        error(res.data)
    } else if (res.status === 401) {
      Vue.use(Message.error("Token 失效，请重新登录"))
      common.User.reset()
    } else if (res.status >= 500) {
      Vue.use(Message.error("服务器开小差"))
    }
  }).catch(res => {
    console.log(res)
  })
}

export default {
  register,
  login,
  update,
  uploadAvatar,
  getMyDetail,
  editArticle,
  getArticles,
  getArticleClazz,
  changeArticlePrivate,
  addClazz,
  deleteArticle,
  deleteClazz,
  renameClazz,
  refreshUser,
  refreshArticle
}
