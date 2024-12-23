import axios from "axios";
const path = import.meta.env.VITE_API_URL

const axiosInstance = axios.create({
  baseURL: path, // 后端基礎路徑
  headers: { "Content-Type": "application/json" },
})

// response 攔截器
axiosInstance.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if(error.response && error.response.status && error.response.status==403) {
      console.log('API Error:', error.response?.data.errorDetail);
      window.location.href = "/403";
    }
    return Promise.reject(error);
  }
);


export default axiosInstance;