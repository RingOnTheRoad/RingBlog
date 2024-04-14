import request from '@/utils/request'

// 注册接口
export const userRegisterService = ({ username, password }) =>
  request.post('/users/register', {
    id: '',
    account: username,
    password: password,
    description: '',
    avatar: ''
  })

// 登录接口
export const userLoginService = ({ username, password }) =>
  request.post('/users/login', {
    id: '',
    account: username,
    password: password,
    description: '',
    avatar: ''
  })

// token校验接口
export const userTokenService = (token) =>
  request.post('/users/token', {
    id: '',
    account: '',
    password: '',
    description: '',
    avatar: '',
    token: token
  })

// 获取用户信息
export const userGetMsg = (params) => request.get('/users', { params })

// 更新用户信息
export const userUpdateMsg = (params) => request.put('/users', params)

// 重置密码接口
export const userResetPassword = (params) => request.put('/users/repwd', params)
