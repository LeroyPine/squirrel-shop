const TokenKey = 'Squirrel-Token'
const RefreshTokenKey = 'Squirrel-Refresh-Token'

export function getToken() {
  return window.localStorage.getItem(TokenKey)
}

export function setToken(token) {
  return window.localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  return window.localStorage.removeItem(TokenKey)
}

export function getRefreshToken() {
  return window.localStorage.getItem(RefreshTokenKey)
}

export function setRefreshToken(refreshToken) {
  return window.localStorage.setItem(RefreshTokenKey, refreshToken)
}

export function removeRefreshToken() {
  return window.localStorage.removeItem(RefreshTokenKey)
}
