import axios from 'axios';
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";

// 所有的 Api 接口地址
const apis = {
  register: "/userApi/user/register",
  sendCode: "/userApi/user/sendEmail",
  login: "/userApi/user/login",
  getMyDetail: "/userApi/user/getMyDetail.token",
  getUserDetail: "/userApi/user/getDetail",
  getUsersDetail: "/userApi/user/getDetail",
  updateUser: "/userApi/user/set.token",
  resetPass: "/userApi/user/changePass.token",
  uploadAvatar: "/userApi/avatar/uploadAvatar.token",

  editArticle: "/articleApi/article/editArticle.token",
  deleteArticle: "/articleApi/article/deleteArticle.token",
  getMyArticleClazz: "/articleApi/clazz/getAllClazz",
  getMyArticleStatistics: "/articleApi/article/getStatistics.token",
  getMyArticles: "/articleApi/article/getArticles.token",
  getMyArticle: "/articleApi/article/getMyArticle.token",
  getArticle: "/articleApi/article/getArticle.tok",
  getArticleRecords: "/articleApi/article/getRecords.tok",
  search: "/articleApi/article/search",
  changeArticlePrivate: "/articleApi/article/changePrivate.token",
  getUserRank: "/articleApi/article/getUserRank",
  getVisitRank: "/articleApi/article/getVisitRank",
  likeArticle: "/articleApi/article/like",
  addClazz: "/articleApi/clazz/add.token",
  deleteClazz: "/articleApi/clazz/delete.token",
  renameClazz: "/articleApi/clazz/rename.token",

  deleteFile: "/fileApi/file/delete.token",
  renameFile: "/fileApi/file/rename.token",
  getAllFile: "/fileApi/file/getAllFile.token",
  downFile: "/fileApi/down.tokCors/",
  changeFilePrivate: "/fileApi/file/changePrivate.token",
}

//获取自己信息的方法
const getMyDetail = function () {
  GET(apis.getMyDetail, null, data => {
    for (let f in data)
      common.User[f] = data[f]
    getArticleStatistics()
  })
}

// 获取一个用户的信息
const getUserDetail = function (id) {
  GET(apis.getUserDetail + "/" + id, null, data => {
    common.ArticleNowShow.user = data
  }, data => {
    console.log(data)
  })
}

// 批量获取用户信息
const getUsersDetail = function (userRank, ids) {
  POST(apis.getUsersDetail, {ids: ids}, data => {

    let userRankMap = {}
    for (let u in userRank)
      userRankMap[userRank[u].userId] = userRank[u]

    let userDetailMap = {}
    for (let u in data)
      userDetailMap[data[u].id] = data[u]

    let rank = []
    for (let index in userRankMap) {
      let nowId = userDetailMap[index].id
      for (let fi in userDetailMap[nowId]) {
        userRankMap[nowId][fi] = userDetailMap[nowId][fi]
      }
      rank.push(userRankMap[nowId])
    }
    common.Index.userRank = rank
  }, data => {
    console.log(data)
  })
}

//注册接口
const register = function () {
  POST(apis.register, common.RegisterForm, data => {
    common.LoginForm.email = common.RegisterForm.email
    common.LoginForm.password = common.RegisterForm.password
    login()
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 发送验证码
const sendCode = function () {
  POST(apis.sendCode,
    {email: common.RegisterForm.email},
    (data) => {
      Vue.use(Message.success(data))
    }, (data) => {
      Vue.use(Message.error(data))
    }
  )
}

// 登录
const login = function () {
  POST(apis.login, common.LoginForm, data => {
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

const resetPass = function (oldPass, newPass) {
  POST(apis.resetPass, {
    oldPass: oldPass,
    newPass: newPass
  }, (data) => {
    Vue.use(Message.success(data))
  }, (data) => {
    Vue.set(Message.error(data))
  })
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

// 编辑或添加文章
const editArticle = function () {
  if (common.ArticleNowEdit.id === undefined || common.ArticleNowEdit.id === "") {
    common.ArticleNowEdit.id = -1
    common.ArticleNowEdit.isPrivate = false
  }
  POST(apis.editArticle, common.ArticleNowEdit, data => {
    Vue.use(Message.success(data))
    window.location.href = "/myArticle"
    common.ArticleNowEdit.reset()
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 删除文章
const deleteArticle = function (id) {
  POST(apis.deleteArticle + "/" + id, null, data => {
    Vue.use(Message.success(data))
    common.UserArticle.statistics.number--
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
    common.UserArticle.nowClazzId = clazzId
    common.UserArticle.articles = res
  }, null)
}

const search = function (title, page) {
  GET(apis.search, {title: title, p: page}, data => {
    for (let a in data)
      common.Index.visitRank.push(data[a])
    common.Searcher.hasMore = data.length !== 0
  }, data => {
    console.log(data)
  })
}

// 改变文章的访问权限
const changeArticlePrivate = function (id) {
  POST(apis.changeArticlePrivate + "/" + id, null, data => {
    for (let a in common.UserArticle.articles)
      if (common.UserArticle.articles[a].id === id)
        common.UserArticle.articles[a].private = !common.UserArticle.articles[a].private
  }, null)
}

const likeArticle = function (id) {
  GET(apis.likeArticle, {id: id}, null, null)
}

// 编辑前获取文章现有内容
const getArticleForEdit = function (id) {
  GET(apis.getMyArticle + "/" + id, null, data => {
    common.ArticleNowEdit.clazzId = data.clazzId
    common.ArticleNowEdit.title = data.title
    common.ArticleNowEdit.summary = data.summary
    common.ArticleNowEdit.content = data.content
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 获取展示的文章
const getArticleForShow = function (id, version) {
  let date = null
  if (version !== null)
    date = {v: version}
  GET(apis.getArticle + "/" + id, date, data => {

    for (let f in data)
      common.ArticleNowShow[f] = data[f]

    if (version === null) {
      common.ArticleNowShow.records[0].color = "#409eff"
    } else {
      for (let r in common.ArticleNowShow.records) {
        common.ArticleNowShow.records[r].color = common.ArticleNowShow.records[r].versionId === version ? "#409eff" : ""
      }
    }
    getUserDetail(data.userId)

  }, data => {
    console.log(data)
  })
}

// 获取文章的更新记录
const getArticleRecords = function (id, callback) {
  GET(apis.getArticleRecords + "/" + id, null, data => {
    common.ArticleNowShow.records = data
    common.ArticleNowShow.records.reverse()
    if (callback !== null)
      callback(data)
  }, data => {
    console.log(data)
  })
}

// 获取用户排行榜
const getUserRank = function () {
  GET(apis.getUserRank, null, data => {
    let ids = []
    for (let u in data)
      ids.push(data[u].userId)
    getUsersDetail(data, ids)
  }, data => {
    console.log(data)
  })
}

// 获取文章排行榜
const getVisitRank = function () {
  GET(apis.getVisitRank, null, data => {
    common.Index.visitRank = data
  }, data => {
    console.log(data)
  })
}

// 添加文章分类
const addClazz = function () {
  POST(apis.addClazz, {name: "未命名"}, data => {
    Vue.use(Message.success("添加成功"))
    getArticleClazz(false)
    getArticleStatistics()
  }, data => {
    Vue.use(Message.error("添加失败"))
  })
}

// 删除分类
const deleteClazz = function () {
  POST(apis.deleteClazz + "/" + common.ClazzNowEdit.id, null, data => {
    Vue.use(Message.success(data))
    getArticleClazz(true)
    getArticleStatistics()
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 重命名分类
const renameClazz = function () {
  POST(apis.renameClazz, common.ClazzNowEdit, data => {
    Vue.use(Message.success(data))
    getArticleClazz(true)
  }, data => {
    Vue.use(Message.error(data))
  })
}

// 刷新文章统计信息、分类列表、第一分类下的文章内容
const refreshArticle = function () {
  getArticleStatistics()
  getArticleClazz(true)
}

// 刷新用户信息
const refreshUser = function () {
  getMyDetail()
}

// 删除文件
const deleteFile = function (id) {
  GET(apis.deleteFile + "/" + id, null, (data) => {
    Vue.use(Message.success(data))
    getFileList()
  }, (data) => {
    Vue.use(Message.error(data))
  })
}

// 重命名文件
const renameFile = function () {
  POST(apis.renameFile, {
    id: common.nowRenameFile.id,
    newVal: common.nowRenameFile.name
  }, (data) => {
    getFileList()
    Vue.use(Message.success(data))
    common.nowRenameFile.reset()
  }, (data) => {
    Vue.use(Message.error(data))
    common.nowRenameFile.reset()
  })
}

const changeFilePrivate = function (id) {
  POST(apis.changeFilePrivate + "/" + id, null, (data) => {
    for (let f in common.File.fileList) {
      if (common.File.fileList[f].id === id)
        common.File.fileList[f].private = !common.File.fileList[f].private
    }
  }, null)
}

// 获取文件列表
const getFileList = function () {
  GET(apis.getAllFile, null, (data) => {
    common.File.fileList = data
  }, (data) => {
    console.log(data)
  })
}

// 下载文件
const down = function (id, name) {
  GET(apis.downFile + "/" + id, null, (data) => {
    window.location.href = "/fileApi/download/" + name + "?downId=" + data
  }, null)
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
    } else if (res.status === 402) {
      Vue.use(Message.error("权限被拒绝"))
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
    } else if (res.status === 402) {
      Vue.use(Message.error("权限被拒绝"))
    } else if (res.status >= 500) {
      Vue.use(Message.error("服务器开小差"))
    }
  }).catch(res => {
    console.log(res)
  })
}

export default {
  register,
  sendCode,
  login,
  update,
  resetPass,
  uploadAvatar,
  getMyDetail,
  editArticle,
  getArticles,
  getArticleClazz,
  changeArticlePrivate,
  likeArticle,
  getArticleForShow,
  getArticleRecords,
  search,
  getArticleForEdit,
  addClazz,
  deleteArticle,
  getUserRank,
  getVisitRank,
  deleteClazz,
  renameClazz,
  refreshUser,
  refreshArticle,
  deleteFile,
  renameFile,
  changeFilePrivate,
  getFileList,
  down
}
