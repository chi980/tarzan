import axios from "axios";
// 액세스 토큰을 매개변수로 받아서 Axios 인스턴스를 생성하는 함수
export function createApiInstance(accessToken) {
  return axios.create({
    baseURL: "",
    headers: {
      common: {
        "Content-Type": "application/json",
        Authorization: accessToken,
      },
    },
  });
}
