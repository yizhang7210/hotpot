import Home from './components/Home.vue'

export default [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
  }
];
