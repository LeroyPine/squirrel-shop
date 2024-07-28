import request from '@/utils/request'

export function fetchUserList(data) {
  return request({
    url: '/user/getAllUser',
    method: 'post',
    data
  })
}

export function saveUserInfo(data) {
  return request({
    url: '/user/saveUserInfo',
    method: 'post',
    data
  })
}

export function updateUserInfo(data) {
  return request({
    url: '/user/updateUserInfo',
    method: 'post',
    data
  })
}
