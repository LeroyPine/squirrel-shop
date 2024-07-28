import request from '@/utils/request'

export function countPeople(type) {
  return request({
    url: '/count/user',
    method: 'get',
    params: { type }
  })
}

export function countDayMoney(type) {
  return request({
    url: '/count/money',
    method: 'get',
    params: { type }
  })
}

export function countPoint(type) {
  return request({
    url: '/count/point',
    method: 'get',
    params: { type }
  })
}

export function fetchUserRank() {
  return request({
    url: '/count/userRank',
    method: 'get'
  })
}

