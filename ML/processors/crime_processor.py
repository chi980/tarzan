import pandas as pd
import os
import sys

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from utils.logger import setup_logger

class CrimeProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("crime_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, input_file: str, output_file: str) -> pd.DataFrame:
        """
        범죄 데이터 파일을 읽고, 필요한 컬럼과 행만 남긴 뒤 저장한다.

        Args:
            input_file (str): 원본 CSV 파일 경로
            output_file (str): 처리 후 저장할 CSV 파일 경로

        Returns:
            pd.DataFrame: 처리된 데이터프레임
        """

        # 파일 읽기
        self.logger.info(f"범죄 데이터 처리 시작: {input_file}")
        df = self.load(input_file)

        # 전처리
        df_processed = self.preprocess(df)
        print(df_processed.head())
        
        # 저장
        self.save(df_processed, output_file)
        self.logger.info(f"범죄 데이터 저장 완료: {output_file}")

        return df
    
    def load(self, input_file: str) -> pd.DataFrame:
        input_file_path = os.path.join(self.raw_data_path, input_file)
        df = pd.read_csv(input_file_path, header=None, index_col=False, encoding="utf-8")
        return df
    
    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        
        # 2, 4, 6, 8, 10, 12번째 컬럼만 남기기 (index: 1,3,5,7,9,11)
        cols_to_keep = [1, 3, 5, 7, 9, 11]
        df = df[cols_to_keep]

         # 2번째 행(index=2)을 header로 설정
        df.columns = df.iloc[2]

        # 1, 2, 4번째 행 삭제 (index: 0,1,3)
        df = df.drop(index=[0, 1, 2, 3, 4])
        
        # 인덱스 리셋
        df = df.reset_index(drop=True)
        return df
        
    def save(self, df: pd.DataFrame, output_file: str):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        df.to_csv(output_file_path, index=False, encoding="utf-8")

class PopulationProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("crime_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, input_file: str, output_file: str) -> pd.DataFrame:
        # 파일 읽기
        self.logger.info(f"인구 데이터 처리 시작: {input_file}")
        df = self.load(input_file)

        # 전처리
        df_processed = self.preprocess(df)
        print(df_processed.head())
        
        # 저장
        self.save(df_processed, output_file)
        self.logger.info(f"인구 데이터 저장 완료: {output_file}")

        return df
    
    def load(self, input_file: str) -> pd.DataFrame:
        input_file_path = os.path.join(self.raw_data_path, input_file)
        df = pd.read_csv(input_file_path, header=None, index_col=False, encoding="utf-8")
        return df
    
    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        
        # 2, 4, 6, 8, 10, 12번째 컬럼만 남기기 (index: 1,3,5,7,9,11)
        cols_to_keep = [1, 3, 6, 9, 13]
        df = df[cols_to_keep]

         # 2번째 행(index=2)을 header로 설정
        df.columns = df.iloc[1]

        # 1, 2, 4번째 행 삭제 (index: 0,1,3)
        df = df.drop(index=[0, 1, 2, 3])
        
        # 인덱스 리셋
        df = df.reset_index(drop=True)
        return df
        
    def save(self, df: pd.DataFrame, output_file: str):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        df.to_csv(output_file_path, index=False, encoding="utf-8")

class CrimRatioProcessor:
    def __init__(self, config):
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("crime_ratio_processor", config["logging"]["log_file"])
        os.makedirs(self.processed_data_path, exist_ok=True)
    
    def run(self, crime_input_file, population_input_file, crime_ratio_output_file):
        # 파일 로드
        crime_df, population_df = self.load(crime_input_file, population_input_file)
        
        # 전처리 및 범죄율 계산
        crime_ratio_df = self.preprocess(crime_df, population_df)
        
        # 저장
        self.save(crime_ratio_df, crime_ratio_output_file)
    
    def load(self, crime_input_file, population_input_file):
        crime_file_path = os.path.join(self.processed_data_path, crime_input_file)
        population_file_path = os.path.join(self.processed_data_path, population_input_file)
        
        crime_df = pd.read_csv(crime_file_path, encoding='utf-8')
        population_df = pd.read_csv(population_file_path, encoding='utf-8')
        
        return crime_df, population_df
    
    def preprocess(self, crime_df, population_df):
        # 자치구별로 병합
        merged_df = crime_df.merge(population_df[['동별(2)', '계 (명)']], left_on='자치구별(2)', right_on='동별(2)')
        
        # 인구수 천 명 단위로 변환
        merged_df['인구수_천명단위'] = merged_df['계 (명)'] / 1000
        
        # 각 범죄 컬럼에 대해 범죄율 계산
        crime_columns = ['소계', '살인', '강도', '강간·강제추행', '절도']
        for col in crime_columns:
            merged_df[f'{col}_범죄율'] = merged_df[col] / merged_df['인구수_천명단위']
        
        # 결과 데이터프레임
        result = merged_df[['자치구별(2)'] + [f'{col}_범죄율' for col in crime_columns]]
        
        return result
    
    def save(self, crime_ratio_df, output_file):
        output_file_path = os.path.join(self.processed_data_path, output_file)
        crime_ratio_df.to_csv(output_file_path, index=False, encoding='utf-8')

if __name__ == "__main__":
    import yaml
    
    from processors.crime_processor import CrimeProcessor
    from processors.crime_processor import PopulationProcessor
    from processors.crime_processor import CrimRatioProcessor

    # config 파일 불러오기
    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)

    crimeProcessor = CrimeProcessor(config)
    populationProcessor = PopulationProcessor(config)
    crimeRatioProcessor = CrimRatioProcessor(config)


    # crimeProcessor.run("구별범죄.csv", "구별범죄건수_정리.csv")
    # populationProcessor.run("구별인구.csv", "구별인구수_정리.csv")
    crimeRatioProcessor.run("구별범죄건수_정리.csv", "구별인구수_정리.csv", "구별범죄율_정리.csv")
    
