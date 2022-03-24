import axios from 'axios'

function get(){
  axios.get('/api').then(res=>(console.log(res)));
}
export{
  get
}
