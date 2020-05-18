import Vue from 'vue'
import {BNavbar, BNavbarBrand, BFormInput, BPagination, BTable} from 'bootstrap-vue'
import VueRouter from 'vue-router'

import App from './App.vue'
import store from './store'
import routes from './routes'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VueRouter);
Vue.component('b-navbar', BNavbar);
Vue.component('b-navbar-brand', BNavbarBrand);
Vue.component('b-form-input', BFormInput);
Vue.component('b-pagination', BPagination);
Vue.component('b-table', BTable);

Vue.config.productionTip = false;

const router = new VueRouter({
  mode: 'history',
  routes
});

new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
});
