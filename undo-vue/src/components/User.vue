<template>
  <div class="user-box">
    <div class="user-body">
      <div class="user-header">
        <el-upload :show-file-list="false"
                   :http-request="uploadAvatar"
                   class="avatar-uploader"
                   action="">
          <img class="user-avatar-img" :src="user.avatar" alt="头像" title="点击更换头像"/>
        </el-upload>
      </div>
      <div class="user-detail">
        <table class="user-detail-table">
          <tr>
            <td class="user-detail-table-label">ID</td>
            <td>{{ user.id }}</td>
          </tr>
          <tr>
            <td class="user-detail-table-label">昵称</td>
            <td @dblclick="edit('username')">
              <span v-show="nowEdit.field!=='username'">{{ user.username }}</span>
              <input id="username" @blur="submit" v-show="nowEdit.field==='username'" v-model="user.username"
                     type="text"/>
            </td>
          </tr>
          <tr>
            <td class="user-detail-table-label">邮箱</td>
            <td @dblclick="edit('email')">
              <span v-show="nowEdit.field!=='email'">{{ user.email }}</span>
              <input id="email" @blur="submit" v-show="nowEdit.field==='email'" v-model="user.email" type="text"/>
            </td>
          </tr>
          <tr>
            <td class="user-detail-table-label">签名</td>
            <td @dblclick="edit('sign')">
              <span v-show="nowEdit.field!=='sign'">{{ user.sign }}</span>
              <input id="sign" @blur="submit" v-show="nowEdit.field==='sign'" v-model="user.sign" type="text"/>
            </td>
          </tr>
          <tr>
            <td class="user-detail-table-label">文章</td>
            <td>{{ userArticle.statistics.number }}</td>
          </tr>
          <tr>
            <td class="user-detail-table-label">获赞</td>
            <td>{{ userArticle.statistics.like }}</td>
          </tr>
          <tr>
            <td class="user-detail-table-label">浏览量</td>
            <td>{{ userArticle.statistics.visit }}</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import common from "./js/common";
import ajax from "./js/ajax";
import Vue from "vue";
import {Message} from "element-ui";

export default {
  name: "User",
  data() {
    return {
      user: common.User,
      userArticle: common.UserArticle,
      nowEdit: common.UserNowEdit,
    }
  }, methods: {
    edit(field) {
      this.nowEdit.field = field
    }, submit() {
      if (this.nowEdit.field === 'username') {
        this.nowEdit.newVal = this.user.username
      } else if (this.nowEdit.field === 'email') {
        this.nowEdit.newVal = this.user.email
      } else if (this.nowEdit.field === 'sign') {
        this.nowEdit.newVal = this.user.sign
      }
      ajax.update()
      this.nowEdit.field = ""
    }, uploadAvatar(parm) {
      ajax.uploadAvatar(parm.file)
    }
  }, watch: {
    nowEdit: {
      deep: true,
      handler: function (val) {
        if (val.field === '') return
        if (val.field === 'username') {
          setTimeout("document.getElementById(\"username\").focus()", 50);
        } else if (val.field === 'email') {
          setTimeout("document.getElementById(\"email\").focus()", 50);
        } else if (val.field === 'sign') {
          setTimeout("document.getElementById(\"sign\").focus()", 50);
        }
      }
    },
    user: {
      deep: true,
      handler: function (val) {
        if (!val.isLogin) {
          this.$router.push('/').catch(res => console.log(res));
        }
      }
    }
  }
}
</script>

<style scoped>
.user-box {
  background-image: url("/static/bg.png");
  width: 100%;
  margin-top: 10px;
  padding-top: 20px;
  padding-bottom: 20px;
  user-select: none;
}

.user-body {
  width: 40%;
  margin-left: 30%;
  background-color: #EFF0F1;
  border: #EFF0F1 solid 1px;
  border-radius: 5px;
}

.user-body:hover {
  transition: 0.5s;
  box-shadow: 3px 3px 10px #bebebe;
}

.user-header {
  text-align: center;
}

.user-avatar-img {
  width: 150px;
  height: 150px;
  border-radius: 100px;
  margin-top: 30px;
  display: inline-block;
}

.user-detail {
  width: 60%;
  margin-left: 20%;
  margin-top: 30px;
  user-select: none;
  margin-bottom: 30px;
}

.user-detail-table {
  width: 80%;
  margin-left: 10%;
  border-collapse: collapse;
}

.user-detail-table-label {
  width: 20%;
}

.user-detail-table td {
  text-align: center;
  height: 40px;
  color: rgb(100, 100, 100);
  font-size: 15px;
  border: rgb(222, 222, 222) solid 2px;
}

.user-detail-table input {
  height: 100%;
  width: 99%;
  border: 0;
  outline: none;
  font-size: 20px;
  background-color: #EFF0F1;
}
</style>
