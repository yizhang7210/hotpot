import Home from './components/Home.vue'
import ServiceDetails from './components/ServiceDetails.vue'

export default [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
  },
  {
    path: '/services/:sid',
    component: ServiceDetails,
  }
];
