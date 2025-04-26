# collectors/real_estate_crawler.py
import sys
import csv
import os
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import concurrent.futures
import requests
import pandas as pd
import xml.etree.ElementTree as ET
from utils.logger import setup_logger
from config.lawd_codes import SEOUL_GU_CODES
from urllib import parse

class RealEstateCrawler:
    def __init__(self, config):
        self.api_key = config["public_data_api"]["key"]
        self.urls = config["public_data_api"]["url"]["trade"]
        self.raw_data_path = config["data"]["raw_data_path"]
        self.logger = setup_logger("real_estate_crawler", config["logging"]["log_file"])

        os.makedirs(self.raw_data_path, exist_ok=True)

    def _request_data(self, url):
        """OpenAPI 요청 + 응답코드 검사"""
        response = requests.get(url)
        if response.status_code == 200:
            # 응답 인코딩에 따라 설정
            if response.encoding is None:
                response.encoding = 'utf-8'
            try:
                root = ET.fromstring(response.text)
                result_code = root.find(".//resultCode").text

                if result_code == "000":
                    return response.text
                else:
                    result_msg = root.find(".//resultMsg").text
                    self.logger.error(f"API 오류 발생: {url} {result_code} - {result_msg}")
                    return None
            except ET.ParseError:
                self.logger.error(f"XML 파싱 오류 발생: {url}")
                return None
        else:
            self.logger.error(f"HTTP 오류 발생: {response.status_code} {url}")
            return None

    def _parse_xml_to_dataframe(self, xml_data):
        """XML → DataFrame 변환"""
        root = ET.fromstring(xml_data)
        items = root.findall(".//item")

        records = []
        for item in items:
            record = {}
            for elem in item:
                record[elem.tag] = elem.text
            records.append(record)

        df = pd.DataFrame(records)
        return df

    def _save_csv(self, df, filename):
        """CSV 저장"""
        save_path = os.path.join(self.raw_data_path, filename)
        df.to_csv(save_path, index=False, encoding="utf-8", quoting=csv.QUOTE_ALL)
        self.logger.info(f"저장 완료: {save_path}")

    def run(self):
        self.logger.info("실거래가 데이터 수집 시작")

        building_info = {
            "apartment": {"url": self.urls["apartment"], "filename": "apartment.csv"},
            "single_multi_family": {"url": self.urls["single_multi_family"], "filename": "single_multi_family.csv"},
            "officetel": {"url": self.urls["officetel"], "filename": "officetel.csv"},
            "multiflex": {"url": self.urls["multiflex"], "filename": "multiflex.csv"},
        }

        tasks = []
        year_month_list = self._generate_year_month_list(2023, 1, 2023, 12)

        for building_type, info in building_info.items():
            for gu_name, lawd_cd in SEOUL_GU_CODES.items():
                for year_month in year_month_list:
                    tasks.append((building_type, info, gu_name, lawd_cd, year_month))

        with concurrent.futures.ThreadPoolExecutor(max_workers=10) as executor:
            futures = [executor.submit(self._process_task, *task) for task in tasks]

            for future in concurrent.futures.as_completed(futures):
                if future.exception() is not None:
                    self.logger.error(f"에러 발생: {future.exception()}")

    def _process_task(self, building_type, info, gu_name, lawd_cd, year_month):
        """단일 task 처리"""
        params = f'?{parse.quote_plus("ServiceKey")}={self.api_key}&' + parse.urlencode({
            "pageNo": '1',
            "numOfRows": '9999',
            "LAWD_CD": lawd_cd,
            "DEAL_YMD": year_month
        })
        self.logger.info(f"{building_type} {gu_name} {year_month} 데이터 요청 중...")

        full_url = info["url"] + params
        xml_data = self._request_data(full_url)
        if xml_data:
            df = self._parse_xml_to_dataframe(xml_data)
            if not df.empty:
                save_path = os.path.join(self.raw_data_path, info["filename"])
                if os.path.exists(save_path):
                    try:
                        df_existing = pd.read_csv(save_path, encoding="utf-8", quoting=csv.QUOTE_ALL)
                        all_columns = sorted(set(df_existing.columns) | set(df.columns))  # 컬럼 합집합 정렬
                        df_existing = df_existing.reindex(columns=all_columns)
                        df = df.reindex(columns=all_columns)
                        df = pd.concat([df_existing, df], ignore_index=True)
                    except Exception as e:
                        self.logger.error(f"기존 CSV 로드 실패: {e}")
                else:
                    all_columns = sorted(df.columns)
                    df = df.reindex(columns=all_columns)

                self._save_csv(df, info["filename"])
            else:
                self.logger.warning(f"{building_type} {gu_name} {year_month} 데이터가 비어있음.")
        else:
            self.logger.error(f"{building_type} {gu_name} {year_month} API 요청 실패.")

    def _generate_year_month_list(self, start_year: int, start_month: int, end_year: int, end_month: int):
        """start_year, start_month부터 end_year, end_month까지 (YYYYMM 리스트 반환)"""
        from datetime import datetime
        from dateutil.relativedelta import relativedelta

        start_date = datetime(year=start_year, month=start_month, day=1)
        end_date = datetime(year=end_year, month=end_month, day=1)

        year_months = []
        while start_date <= end_date:
            year_months.append(start_date.strftime("%Y%m"))
            start_date += relativedelta(months=1)

        return year_months

if __name__ == "__main__":
    import yaml
    from collectors.real_estate_crawler import RealEstateCrawler

    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)

    crawler = RealEstateCrawler(config)
    crawler.run()
