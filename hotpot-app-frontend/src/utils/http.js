import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  get(subUrl) {
		return axios.get(SERVER_URL + subUrl);
  }
}
