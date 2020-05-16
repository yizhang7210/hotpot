import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import http from './utils/http';

Vue.use(Vuex);

export default new Vuex.Store({
	plugins: [createPersistedState({key: '__hotpot'})],
	state: {
		services: [],
	},
	mutations: {
		updateServices(state, services) {
			state.services = services;
		},
	},
	actions: {
		async fetchAllServices(context) {
			const services = await http.get('v1/services');
			context.commit('updateServices', services.data);
    }
	}
})
