import axios from 'axios'

// 创建axios实例
const service = axios.create({
  timeout: 15000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
  config.data = config.data || {}
  config.data.platform = 0;
  config.data.version = 100;
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非200是抛错 可结合自己业务进行修改
     */
    const res = response.data
    return res
  },
  error => {
    console.log('err' + error)// for debug
  }
)

export default service
