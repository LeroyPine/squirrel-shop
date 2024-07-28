import request from '@/utils/request'

export function fetchProductList(data) {
  return request({
    url: '/product/getAllProduct',
    method: 'post',
    data
  })
}

export function saveProductInfo(data) {
  return request({
    url: '/product/saveProduct',
    method: 'post',
    data
  })
}

export function updateProductInfo(data) {
  return request({
    url: '/product/updateProduct',
    method: 'post',
    data
  })
}
