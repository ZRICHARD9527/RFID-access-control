import Vue from 'vue'
import App from './App.vue'
import router from './router.js'
import ElementUI from 'element-ui'
import './plugins/element'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'
import axios from 'axios'
import store from './store'
import JsonExcel from 'vue-json-excel'
Vue.prototype.$http = axios
axios.defaults.baseURL = 'http://192.168.43.43:8133/'

axios.interceptors.request.use(config => {
  config.headers.token = window.sessionStorage.getItem('token')
  return config
})

Vue.config.productionTip = false

Vue.component('downloadExcel', JsonExcel)

Vue.use(ElementUI)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
