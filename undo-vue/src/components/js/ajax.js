import axios from 'axios';
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";

const apis = {
  registerUrl: "/userApi/user/register",
  loginUrl: "/userApi/user/login",
  getMyDetailUrl: "/userApi/user/getMyDetail.token",
  updateUser: "/userApi/user/set.token",
  uploadAvatar: "/userApi/user/uploadAvatar.token"
}

const getMyDetail = function () {
  axios.get(
    apis.getMyDetailUrl,
    {
      headers: {
        "token": common.User.token
      }
    }
  ).then(res => {
    res = res.data;
    if (res.status === 200) {
      res = res.data
      for (let f in res) {
        common.User[f] = res[f]
      }
    }
  })
}

const register = function () {
  axios.post(
    apis.registerUrl,
    common.RegisterForm,
  ).then(res => {
    res = res.data
    if (res.status === 200) {
      common.LoginForm.email = common.RegisterForm.email
      common.LoginForm.password = common.RegisterForm.password
      login()
    } else {
      Vue.use(Message.error(res.data))
    }
  }).catch(
    Vue.use(Message.error("注册失败！"))
  )
}

const login = function () {
  axios.post(
    apis.loginUrl,
    common.LoginForm
  ).then(res => {
    res = res.data;
    if (res.status === 200) {
      Vue.use(Message.success("登录成功！"))
      common.LoginDialogConfig.isDialog = false
      common.User.token = res.data
      common.User.isLogin = true
      common.LoginForm.reset()
      getMyDetail()
    } else {
      Vue.use(Message.error(res.data))
    }
  }).catch(res => {
    Vue.use(Message.error("登录失败！"))
  });
}

const update = function () {
  axios.post(
    apis.updateUser,
    common.UserNowEdit,
    {
      headers: {
        "token": common.User.token
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
  })
}

const uploadAvatar = function (file) {
  if (file.size / 1024 / 1024 > 2) {
    Vue.use(Message.error("上传头像图片大小不能超过 2MB!"))
    return
  }
  let data = new FormData()
  data.append('file',file)
  axios.post(
    apis.uploadAvatar,
    data,
    {
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

export default {
  register, login, update, uploadAvatar
}
