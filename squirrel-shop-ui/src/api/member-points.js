import request from '@/utils/request'

export function fetchMemberConfigList(data) {
  return request({
    url: '/memberPointConfig/getAllMemberPointsConfig',
    method: 'get'
  })
}

export function fetchMemberPointsList(data) {
  return request({
    url: '/memberPoints/list',
    method: 'post',
    data
  })
}

export function buyProductDetail(data) {
  return request({
    url: '/memberPoints/buyProductDetail',
    method: 'post',
    data
  })
}

export function buyProduct(data) {
  return request({
    url: '/memberPoints/buyProduct',
    method: 'post',
    data
  })
}

export function redeemPrizes(data) {
  return request({
    url: '/memberPoints/redeemPrizes',
    method: 'post',
    data
  })
}

export function fetchHistoryList(data) {
  return request({
    url: '/memberPoints/historyList',
    method: 'post',
    data
  })
}

