//axios-init.js
import axios from 'axios';

   let loadingInstance; //创建Loading 的实例
axios.defaults.baseURL = appConfig.xhr.baseURL; // 配置axios请求的地址
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=utf-8';
axios.defaults.crossDomain = true;
axios.defaults.withCredentials = true;  //设置cross跨域 并设置访问权限 允许跨域携带cookie信息
axios.defaults.headers.common['Authorization'] = ''; // 设置请求头为 Authorization

// axios.interceptors.request.use(
//     config => {
//         // 每次发送请求之前判断vuex中是否存在token
//         // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
//         // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
//         const token = window.localStorage.getItem("token");
//         token && (config.headers.Authorization = token);
//         return config;
//     },
//     error => {
//         return Promise.error(error);
//     }
// );

axios.interceptors.request.use(function (config) {
    config.data = qs.stringify(config.data);  //qs处理


    return config;
}, function (error) {
    return Promise.reject(error);
});