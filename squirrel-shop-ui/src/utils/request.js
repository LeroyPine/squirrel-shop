import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getRefreshToken, getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    const url = config.url
    if (url.includes('refreshToken')) {
      if (!getRefreshToken()) {
        return Promise.reject()
      }
      config.headers['refresh-token'] = getRefreshToken()
      return config
    }
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['authorization-token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 是否正在刷新的标记
let isRefreshing = false
// 重试队列
let requests = []

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    const url = response.config.url
    const config = response.config
    const pro = response.config.proxy
    console.log(pro)
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      if (url.includes('/logout')) {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
        return Promise.reject(new Error(res.message || 'Error'))
      }
      // 1003:刷新token失败,跳转到登陆页面  1005:token过期,调用刷新token接口
      if (res.code === 1005) {
        if (!isRefreshing) {
          isRefreshing = true
          // 调用刷新token的接口
          return store.dispatch('user/refreshToken').then(res => {
            console.log('拴心token结果' + res)
            // token 刷新后将数组的方法重新执行
            requests.forEach((cb) => cb())
            requests = [] // 重新请求完清空
            // 重新设置 url 确保不会重复 baseURL
            if (config.url.startsWith(process.env.VUE_APP_BASE_API)) {
              config.url = config.url.replace(process.env.VUE_APP_BASE_API, '')
            }
            return service(config)
          }).catch(err => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
            return Promise.reject(err)
          }).finally(() => {
            isRefreshing = false
          })
        } else {
          // 返回未执行 resolve 的 Promise
          return new Promise(resolve => {
            // 用函数形式将 resolve 存入，等待刷新后再执行
            requests.push(() => {
              if (config.url.startsWith(process.env.VUE_APP_BASE_API)) {
                config.url = config.url.replace(process.env.VUE_APP_BASE_API, '')
              }
              resolve(service(config))
            })
          })
        }
      } else if (res.code === 1003) {
        MessageBox.confirm('你的登录已到期,请重新登陆!', '确认登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
        return res
      }
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      if (url.includes('/login') || url.includes('/refreshToken')) {
        return response
      } else {
        return res
      }
    }
  }
  ,
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
