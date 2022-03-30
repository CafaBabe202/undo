import axios from 'axios';
import common from "./common";
import Vue from "vue";
import {Message} from "element-ui";

const apis = {
  registerUrl: "/api/user/register",
  loginUrl: "/api/user/login",
  getMyDetailUrl: "/api/user/getMyDetail.token"
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


export default {
  register, login
}
