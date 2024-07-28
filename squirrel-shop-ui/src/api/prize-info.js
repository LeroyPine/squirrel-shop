import request from '@/utils/request'

export function fetchPrizeList(data) {
  return request({
    url: '/prize/getAllPrize',
    method: 'post',
    data
  })
}

export function savePrizeInfo(data) {
  return request({
    url: '/prize/savePrize',
    method: 'post',
    data
  })
}

export function updatePrizeInfo(data) {
  return request({
    url: '/prize/updatePrize',
    method: 'post',
    data
  })
}
