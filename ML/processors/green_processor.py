import pandas as pd
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from utils.logger import setup_logger

class GreenProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("crime_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, input_file: str, output_file: str) -> pd.DataFrame:
        """
        구별녹지 데이터 파일을 읽고, 필요한 컬럼과 행만 남긴 뒤 저장한다.

        Args:
            input_file (str): 원본 CSV 파일 경로
            output_file (str): 처리 후 저장할 CSV 파일 경로

        Returns:
            pd.DataFrame: 처리된 데이터프레임
        """

        # 파일 읽기
        self.logger.info(f"녹지 데이터 처리 시작: {input_file}")
        df = self.load(input_file)

        # 전처리
        df_processed = self.preprocess(df)
        print(df_processed.head())
        
        # 저장
        self.save(df_processed, output_file)
        self.logger.info(f"녹지 데이터 저장 완료: {output_file}")

        return df
    
    def load(self, input_file: str) -> pd.DataFrame:
        input_file_path = os.path.join(self.raw_data_path, input_file)
        df = pd.read_csv(input_file_path, header=None, index_col=False, encoding="utf-8")
        return df
    
    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        
        # 1, 2, 3번째 열만 남기기
        cols_to_keep = [1, 2, 3]
        df = df[cols_to_keep]

        # 3번째 행을 헤더로
        df.columns = df.iloc[2]

        # 헤더 설정 후 불필요한 행 삭제
        df = df.drop(index=[0, 1, 2, 3])
        
        # 인덱스 리셋
        df = df.reset_index(drop=True)
        return df
        
    def save(self, df: pd.DataFrame, output_file: str):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        df.to_csv(output_file_path, index=False, encoding="utf-8")

class AreaProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("crime_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, input_file: str, output_file: str) -> pd.DataFrame:
        """
        구별면적 데이터 파일을 읽고, 필요한 컬럼과 행만 남긴 뒤 저장한다.

        Args:
            input_file (str): 원본 CSV 파일 경로
            output_file (str): 처리 후 저장할 CSV 파일 경로

        Returns:
            pd.DataFrame: 처리된 데이터프레임
        """

        # 파일 읽기
        self.logger.info(f"면적 데이터 처리 시작: {input_file}")
        df = self.load(input_file)

        # 전처리
        df_processed = self.preprocess(df)
        print(df_processed.head())
        
        # 저장
        self.save(df_processed, output_file)
        self.logger.info(f"면적 데이터 저장 완료: {output_file}")

        return df
    
    def load(self, input_file: str) -> pd.DataFrame:
        input_file_path = os.path.join(self.raw_data_path, input_file)
        df = pd.read_csv(input_file_path, header=None, index_col=False, encoding="utf-8")
        return df
    
    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        
        # 1, 2, 3번째 열만 남기기
        cols_to_keep = [1, 2, 3]
        df = df[cols_to_keep]

        # 3번째 행을 헤더로
        df.columns = df.iloc[2]

        # 헤더 설정 후 불필요한 행 삭제
        df = df.drop(index=[0, 1, 2, 3])
        
        # 인덱스 리셋
        df = df.reset_index(drop=True)
        return df
        
    def save(self, df: pd.DataFrame, output_file: str):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        df.to_csv(output_file_path, index=False, encoding="utf-8")



class GreenRatioProcessor:

    def __init__(self, config):
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("green_ratio_processor", config["logging"]["log_file"])
        os.makedirs(self.processed_data_path, exist_ok=True)
    
    def run(self, green_input_file, area_input_file, green_ratio_output_file):
        # 파일 로드
        green_df,area_df = self.load(green_input_file, area_input_file)
        
        # 전처리 및 범죄율 계산
        green_ratio_df = self.preprocess(green_df, area_df)
        
        # 저장
        self.save(green_ratio_df, green_ratio_output_file)
    
    def load(self, green_input_file, area_input_file):
        green_file_path = os.path.join(self.processed_data_path, green_input_file)
        parea_file_path = os.path.join(self.processed_data_path, area_input_file)
        
        green_df = pd.read_csv(green_file_path, encoding='utf-8')
        area_df = pd.read_csv(parea_file_path, encoding='utf-8')
        
        return green_df, area_df
    
    def preprocess(self, green_df, area_df):
        green_df.columns = ['자치구', '녹지개수', '녹지면적_㎡']
        area_df.columns = ['자치구', '구면적_km2', '구성비']
        # 면적 단위 통일: ㎡ → km² (1 km² = 1,000,000 ㎡)
        green_df['녹지면적_km2'] = green_df['녹지면적_㎡'] / 1_000_000
        
        merged_df = pd.merge(green_df, area_df, on='자치구', how='inner')
        # 구 전체 면적 대비 녹지 면적 비율
        merged_df['녹지비율(%)'] = (merged_df['녹지면적_km2'] / merged_df['구면적_km2']) * 100

        # 필요한 컬럼만 남기기
        result_df = merged_df[['자치구', '녹지비율(%)']]

        
        return result_df
    
    def save(self, crime_ratio_df, output_file):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        crime_ratio_df.to_csv(output_file_path, index=False, encoding='utf-8')

if __name__ == "__main__":
    import yaml
    
    from processors.green_processor import GreenProcessor
    from processors.green_processor import AreaProcessor
    from processors.green_processor import GreenRatioProcessor

    # config 파일 불러오기
    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)
    
    greenProcessor = GreenProcessor(config)
    greenProcessor.run("구별녹지.csv", "구별녹지수_정리.csv")
    
    areaProcessor = AreaProcessor(config)
    areaProcessor.run("구별면적.csv", "구별면적_정리.csv")
    
    greenRatioProcessor = GreenRatioProcessor(config)
    greenRatioProcessor.run("구별녹지수_정리.csv", "구별면적_정리.csv", "구별녹지율_정리.csv")

