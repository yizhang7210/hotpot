import Home from './components/Home.vue'
import MetricDetails from './components/MetricDetails.vue'
import MetricList from './components/MetricList.vue'
import ObjectiveDetails from "./components/ObjectiveDetails";
import ObjectiveList from './components/ObjectiveList.vue'
import ServiceDetails from './components/ServiceDetails.vue'
import ServiceObjectiveDetails from './components/ServiceObjectiveDetails.vue'

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
  },
  {
    path: '/metrics',
    component: MetricList,
  },
  {
    path: '/metrics/:mid',
    component: MetricDetails,
  },
  {
    path: '/services/:sid/objectives/:oid',
    component: ServiceObjectiveDetails,
  },
];
