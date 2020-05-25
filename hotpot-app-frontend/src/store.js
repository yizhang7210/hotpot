import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import http from './utils/http';

Vue.use(Vuex);

export default new Vuex.Store({
	plugins: [createPersistedState({key: '__hotpot'})],
	state: {
		services: [],
		objectives: [],
		metrics: [],
	},
	mutations: {
		updateServices(state, services) {
			state.services = services;
		},
		updateObjectives(state, objectives) {
			state.objectives = objectives;
		},
		updateMetrics(state, metrics) {
			state.metrics = metrics;
		},
	},
	actions: {
		async fetchAllServices(context) {
			const services = await http.get('v1/services');
			context.commit('updateServices', services.data);
    },
		async fetchAllObjectives(context) {
			const objectives = await http.get('v1/objectives');
			context.commit('updateObjectives', objectives.data);
		},
		async fetchAllMetrics(context) {
			const metrics = await http.get('v1/metrics');
			context.commit('updateMetrics', metrics.data);
		}
	}
})
