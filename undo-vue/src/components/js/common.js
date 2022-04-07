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
  token: "",
  reset() {
    User.id = User.username = User.sign = User.email = User.avatar = User.token = ""
    User.isLogin = false
  }
}

const UserArticle = {
  allClazz: [],
  articles: [],
  allNumber: 0,
  allLike: 0,
}

const LoginDialogConfig = {
  isDialog: false,
  isLogin: true,
}

const LoginForm = {
  email: "",
  password: "",
  reset() {
    LoginForm.email = LoginForm.password = ""
  }
}

const LoginFromRules = {
  email: [
    {required: true, message: '请输入昵称', trigger: 'blur'},
    {min: 1, max: 15, message: '长度在 3 到 5 个字符', trigger: 'blur'}
  ], password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ]
}

const RegisterForm = {
  username: "",
  email: "",
  password: "",
  checkPassword: "",
  code: "",
  reset() {
    RegisterForm.username = RegisterForm.email = RegisterForm.password = RegisterForm.checkPassword = RegisterForm.code = ""
  }
}

const RegisterFormRules = {
  username: [
    {required: true, message: '请输入昵称', trigger: 'blur'},
    {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
  ], email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'},
    {min: 3, max: 25, message: '长度在 3 到 15 个字符', trigger: 'blur'}
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

const UserNowEdit = {
  field: "",
  newVal: ""
}

export default {
  MeConfig,
  LoginDialogConfig,
  LoginFromRules,
  User,
  UserArticle,
  LoginForm,
  RegisterForm,
  RegisterFormRules,
  UserNowEdit
}
