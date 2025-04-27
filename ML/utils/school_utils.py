# 가장 가까운 초등학교 거리 계산
# 500m내 초등학교 갯수 계산
# 5±7km 내 중고등학교 갯수 계산

import numpy as np

def haversine_np(lat1, lon1, lat2, lon2):
    """벡터화된 Haversine 공식 (단위: m)"""
    R = 6371000  # 지구 반지름 (미터)

    lat1 = np.radians(lat1)
    lon1 = np.radians(lon1)
    lat2 = np.radians(lat2)
    lon2 = np.radians(lon2)

    dlat = lat2 - lat1
    dlon = lon2 - lon1

    a = np.sin(dlat/2.0)**2 + np.cos(lat1) * np.cos(lat2) * np.sin(dlon/2.0)**2
    c = 2 * np.arcsin(np.sqrt(a))

    return R * c

def calculate_nearest_elementary_distance(lat, lon, school_df):
    elem_df = school_df[school_df["학교급구분"] == "초등학교"]
    distances = haversine_np(lat, lon, elem_df["위도"].values, elem_df["경도"].values)
    return distances.min()

def count_elementary_within_500m(lat, lon, school_df):
    elem_df = school_df[school_df["학교급구분"] == "초등학교"]
    distances = haversine_np(lat, lon, elem_df["위도"].values, elem_df["경도"].values)
    return (distances <= 500).sum()

def count_middle_high_within_range(lat, lon, school_df, min_km=5, max_km=7):
    middle_high_df = school_df[school_df["학교급구분"].isin(["중학교", "고등학교"])]
    distances = haversine_np(lat, lon, middle_high_df["위도"].values, middle_high_df["경도"].values) / 1000  # km 단위
    return ((distances >= min_km) & (distances <= max_km)).sum()
