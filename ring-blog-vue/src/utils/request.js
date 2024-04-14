import axios from 'axios'
import { useUserStore } from '@/stores'
import router from '@/router'

// 请求后端地址
const baseURL = 'http://localhost:80'

// axios实例
const instance = axios.create({
  baseURL,
  timeout: 10000
})

// 请求拦截
instance.interceptors.request.use(
  (config) => {
    // 往请求头添加token
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = userStore.token
    }
    return config
  },
  (err) => Promise.reject(err)
)

// 响应拦截
instance.interceptors.response.use(
  (res) => {
    // 通过后端code反应成功失败
    if (res.data.code === 1) {
      return res
    }
    ElMessage.error(res.data.data || '服务异常')
    return Promise.reject(res.data)
  },
  (err) => {
    if (err.response?.status === 401) {
      router.push('/login')
    }

    ElMessage.error(err.response.data.data || '服务异常')
    return Promise.reject(err)
  }
)

export default instance
export { baseURL }
