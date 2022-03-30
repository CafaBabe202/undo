<template>
  <div>
    <!-- 对话框 -->
    <el-dialog :title="title" :visible.sync="config.isDialog" :width="width" @close="closeDialog">
      <!-- 登录表单 -->
      <div v-show="config.isLogin">
        <el-form :model="loginForm" :rules="loginFormRules" ref="loginFormRules" label-width="80px"
                 label-position="top">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="loginForm.email"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitLoginForm('loginFormRules')">立即登录</el-button>
            <el-button @click="resetLoginForm('loginFormRules')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 注册表单 -->
      <div v-show="!config.isLogin">
        <el-form :model="registerForm" :rules="registerFormRules" ref="registerFormRules" label-width="80px"
                 label-position="left">
          <el-form-item label="用户昵称" prop="username">
            <el-input type="string" v-model="registerForm.username"/>
          </el-form-item>
          <el-form-item label="绑定邮箱" prop="email">
            <el-input type="email" v-model="registerForm.email"/>
          </el-form-item>
          <el-form-item label="登录密码" prop="password">
            <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPassword">
            <el-input type="password" v-model="registerForm.checkPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-input type="email" v-model="registerForm.code"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitRegisterForm('registerFormRules')">立即注册</el-button>
            <el-button @click="resetRegisterForm('registerFormRules')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>

</template>

<script>

import common from "./js/common";
import ajax from "./js/ajax";

export default {
  name: "LoginDialog",
  data() {
    return {
      width: "400px",
      title: "登录",
      config: common.LoginDialogConfig,
      loginForm: common.LoginForm,
      loginFormRules: common.LoginFromRules,
      registerForm: common.RegisterForm,
      registerFormRules: common.RegisterFormRules
    }
  }, methods: {
    closeDialog() {
    }, submitLoginForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          ajax.login()
        } else {
          return false
        }
      });
    }, resetLoginForm(formName) {
      common.LoginForm.reset()
    }, submitRegisterForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          ajax.register()
        } else {
          return false;
        }
      });
    }, resetRegisterForm(formName) {
      common.RegisterForm.reset()
    }
  }
}
</script>

<style scoped>
</style>
