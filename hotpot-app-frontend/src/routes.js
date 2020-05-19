import Home from './components/Home.vue'
import ServiceDetails from './components/ServiceDetails.vue'
import ObjectiveList from './components/ObjectiveList.vue'
import ObjectiveDetails from "./components/ObjectiveDetails";

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
  },
  {
    path: '/objectives',
    component: ObjectiveList,
  },
  {
    path: '/objectives/:oid',
    component: ObjectiveDetails,
  }
];
