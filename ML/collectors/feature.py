# 📦 특성 엔지니어링 실전 코드
import json
import requests
import os
import time
from datetime import datetime
from tqdm import tqdm

KAKAO_API_KEY = os.getenv("KAKAO_REST_API_KEY")

def find_nearest_subway(lat, lng, retries=3):
    url = "https://dapi.kakao.com/v2/local/search/keyword.json"
    headers = {
        "Authorization": f"KakaoAK {KAKAO_API_KEY}"
    }
    params = {
        "query": "지하철역",
        "x": lng,
        "y": lat,
        "radius": 2000,  # 2km 반경 검색
        "sort": "distance"
    }
    
    for attempt in range(retries):
        try:
            response = requests.get(url, headers=headers, params=params)
            if response.status_code == 200:
                result = response.json()
                if result['documents']:
                    nearest = result['documents'][0]
                    return float(nearest['distance'])
                else:
                    return None
            else:
                print(f"Error {response.status_code} at ({lat}, {lng})")
        except Exception as e:
            print(f"Exception {e} at ({lat}, {lng})")
        time.sleep(1)
    return None

def feature_engineering(input_file: str, output_file: str):
    with open(input_file, "r", encoding="utf-8") as f:
        data = json.load(f)
    
    now = datetime.now()
    current_year = now.year

    enriched_data = []

    for item in tqdm(data, desc="특성 엔지니어링 중"):
        # 거래일자 분해
        if 'transaction_date' in item and item['transaction_date']:
            try:
                transaction_date = datetime.strptime(item['transaction_date'], "%Y.%m.%d")
                item['transaction_year'] = transaction_date.year
                item['transaction_month'] = transaction_date.month
                item['transaction_day'] = transaction_date.day
            except:
                item['transaction_year'] = None
                item['transaction_month'] = None
                item['transaction_day'] = None
        else:
            item['transaction_year'] = None
            item['transaction_month'] = None
            item['transaction_day'] = None

        # 건물 나이
        if 'year_built' in item and item['year_built']:
            try:
                item['building_age'] = current_year - int(item['year_built'])
            except:
                item['building_age'] = None
        else:
            item['building_age'] = None
        
        # 건물 종류
        building_name = item.get('building_name', '')
        if '오피스텔' in building_name:
            item['is_officetel'] = 1
        else:
            item['is_officetel'] = 0

        if '아파트' in building_name:
            item['is_apartment'] = 1
        else:
            item['is_apartment'] = 0

        # 지하철 거리
        lat = item.get('latitude')
        lng = item.get('longitude')
        if lat and lng:
            subway_distance = find_nearest_subway(lat, lng)
            item['subway_distance'] = subway_distance
        else:
            item['subway_distance'] = None

        enriched_data.append(item)
    
    with open(output_file, "w", encoding="utf-8") as f:
        json.dump(enriched_data, f, ensure_ascii=False, indent=2)
    
    print(f"Feature Engineering 완료! 파일 저장: {output_file}")

if __name__ == "__main__":
    input_file = "datasets/real_estate_gangnam_with_coords.json"
    output_file = "datasets/real_estate_gangnam_features.json"
    
    feature_engineering(input_file, output_file)
