import pandas as pd
import sys
import os

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from utils.logger import setup_logger

class SchoolProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("school_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, input_file: str, output_file: str) -> pd.DataFrame:
        df = self.load(input_file)
        preprocessed_df = self.preprocess(df)
        self.save(preprocessed_df, output_file)
        return preprocessed_df

    def load(self, input_file):
        input_file_path = os.path.join(self.raw_data_path, input_file)
        df = pd.read_csv(input_file_path, header=0, index_col=False, encoding="cp949")
        return df

    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        # 시도교육청 == 서울특별시교육청
        df = df[df["시도교육청명"] == "서울특별시교육청"]
        
        # 운영상태 == 운영
        df = df[df["운영상태"] == "운영"]
        
        # 필요한 컬럼만 선택
        columns_to_keep = [
            "학교ID", "학교명", "학교급구분",
            "소재지지번주소", "소재지도로명주소",
            "위도", "경도"
        ]
        df = df[columns_to_keep]
        
        # 인덱스 리셋
        df = df.reset_index(drop=True)
        
        return df

    def save(self, df: pd.DataFrame, output_file: str):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        df.to_csv(output_file_path, index=False, encoding="utf-8")

if __name__ == "__main__":
    import yaml
    from processors.school_processor import SchoolProcessor

    # config.yaml 파일 읽어오기
    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)

    processor = SchoolProcessor(config)
    processor.run("전국초중등학교위치표준데이터.csv", "학교_정리.csv")
