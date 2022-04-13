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
  statistics: {
    clazzNum: 0,
    like: 0,
    number: 0,
    visit: 0,
  }, nowClazzId: 0
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

const Searcher = {
  input:"",
}

const UserNowEdit = {
  field: "",
  newVal: ""
}

const ClazzNowEdit = {
  id: "",
  newVal: ""
}

const ArticleNowEdit = {
  id: "",
  title: "",
  summary: "",
  updateSummary: "",
  content: "",
  clazzId: "",
  isPrivate: "",
  reset() {
    this.id = undefined
    this.title = this.summary = this.updateSummary = this.content = this.clazzId = this.isPrivate = ""
  }
}

const ArticleNowShow = {
  summary: "",
  like: "",
  createTime: "",
  clazzId: "",
  updateTime: "",
  id: "",
  visit: "",
  title: "",
  userId: "",
  clazzName: "",
  content: "",
  records: "",
  user: {
    sign: "",
    id: "",
    avatar: "",
    username: ""
  },
  reset() {
    this.summary = this.like = this.createTime = this.updateTime = this.id = this.visit = this.title = this.userId = this.clazzName = this.content = ""
  }
}

const Rank = {
  visitRank: [
    {
      id: "",
      userId: "",
      title: "",
      summary: "",
      like: "",
      visit: "",
      clazzName: "",
      updateTime: "",
    }],
  userRank: []
}

export default {
  MeConfig,
  LoginDialogConfig,
  LoginFromRules,
  User,
  Searcher,
  UserArticle,
  LoginForm,
  RegisterForm,
  RegisterFormRules,
  UserNowEdit,
  ClazzNowEdit,
  ArticleNowEdit,
  ArticleNowShow,
  Rank
}
