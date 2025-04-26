# 주소 → 위경도 변환기
import requests

def kakao_address_to_latlng(address, kakao_rest_api_key):
    """카카오 주소 → 위경도 변환"""
    url = "https://dapi.kakao.com/v2/local/search/address.json"
    headers = {
        "Authorization": f"KakaoAK {kakao_rest_api_key}"
    }
    params = {
        "query": address
    }

    response = requests.get(url, headers=headers, params=params)

    if response.status_code == 200:
        result = response.json()
        documents = result.get("documents")
        if documents:
            first_match = documents[0]
            lat = first_match["y"]
            lng = first_match["x"]
            return lat, lng
        else:
            print(f"주소 검색 결과 없음: {address}")
            return None, None
    else:
        print(f"HTTP 오류: {response.status_code}")
        return None, None

# 사용 예시
if __name__ == "__main__":    
    import yaml
    # config.yaml 파일 읽어오기
    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)

    kakao_rest_api_key = config["kakao_api"]["key"]
    address = "서울특별시 강남구 테헤란로 231"
    lat, lng = kakao_address_to_latlng(address, kakao_rest_api_key)
    print(f"주소: {address}")
    print(f"위도: {lat}, 경도: {lng}")
