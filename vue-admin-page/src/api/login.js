import request from '@/utils/request'

const login = {

  // 登录
  userLogin(param) {
    return request.post('/funeral-manage/system/login', param)
  },

  // 修改密码
  editPassword(data) {
    return request.post('/funeral-manage/system/oldPassword', data)
  }
}

export default login
