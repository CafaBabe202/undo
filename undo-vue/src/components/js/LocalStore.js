import common from "./common";

const storeUser = function () {
  localStorage.setItem("user", JSON.stringify(common.User))
}

const loadUser = function () {
  let user = JSON.parse(localStorage.getItem("user"))
  for(let f in user) {
    common.User[f] = user[f]
  }

}

export default {
  storeUser, loadUser
}
