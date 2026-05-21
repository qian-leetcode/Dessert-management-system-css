import axios from "axios";

const request = axios.create({
    baseURL: 'http://127.0.0.1:8081/api',
    timeout: 10000
})

request.interceptors.request.use(config => {
    const loginStore = loginStore.getState();
    if (loginStore.token) {
        config.headers.token = loginStore.token;
    }
    return config;
})

export default request;