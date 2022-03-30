const MeConfig = {
  isDrawer: false
}

const User = {
  isLogin: false,
  id: "",
  username: "",
  sign: "",
  email: "",
  avatar: "",
  token: ""
}

const LoginDialogConfig = {
  isDialog: false,
  isLogin: true,
}

const LoginForm = {
  id: "",
  password: "",
  reset() {
    LoginForm.id = ""
    LoginForm.password = ""
  }
}

const LoginFromRules = {
  userid: [
    {required: true, message: '请输入昵称', trigger: 'blur'},
    {min: 1, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
  ], password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ]
}

const RegisterForm = {
  userid: "",
  email: "",
  password: "",
  checkPassword: "",
  code: ""
}

const RegisterFormRules = {
  userid: [
    {required: true, message: '请输入昵称', trigger: 'blur'},
    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
  ], email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
  ], password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ], checkPassword: [
    {required: true, message: '请输入验证密码', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ], code: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ]
}

export default {
  MeConfig,
  LoginDialogConfig,
  LoginFromRules,
  User,
  LoginForm,
  RegisterForm,
  RegisterFormRules,
}
