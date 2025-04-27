import pandas as pd
import glob
import sys
import os

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from utils.logger import setup_logger


class PopulationProcessor:
    def __init__(self, config):
        self.raw_data_path = config["data"]["raw_data_path"]
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("population_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)

    def run(self, year: str, output_file: str) -> pd.DataFrame:
        """
        연도별 파일 합치고, 전처리 후 저장까지 실행
        """
        self.logger.info(f"{year}년도 생활인구 데이터 처리 시작")

        df_all = self.merge_files(self.raw_data_path, year)
        result = self.preprocess(df_all)
        self.save(result, output_file)

        return result

    def merge_files(self, folder_path: str, year: str) -> pd.DataFrame:
        """
        연도별 파일을 찾아 합친다
        """
        file_pattern = os.path.join(folder_path, f"동별생활인구/LOCAL_PEOPLE_DONG_{year}*.csv")
        file_list = glob.glob(file_pattern)

        if not file_list:
            self.logger.warning(f"{year}년도에 해당하는 파일이 없습니다.")
            raise FileNotFoundError(f"{year}년도에 해당하는 파일이 없습니다.")

        dfs = []
        for file in file_list:
            df = pd.read_csv(file,
                             sep=',',
                             encoding='utf-8',
                             quotechar='"',
                             dtype=str,
                             index_col=False)
            # 빈 컬럼 제거
            if df.columns[-1] == '':
                df = df.drop(columns=[''])
            dfs.append(df)

        df_all = pd.concat(dfs, ignore_index=True)
        self.logger.info(f"파일 {len(file_list)}개를 성공적으로 병합했습니다.")
        return df_all

    def preprocess(self, df: pd.DataFrame) -> pd.DataFrame:
        """
        데이터프레임을 전처리: 숫자형 변환 및 그룹바이 평균
        """
        cols_to_numeric = [col for col in df.columns if col not in ['기준일ID', '시간대구분', '행정동코드']]

        for col in cols_to_numeric:
            df[col] = pd.to_numeric(df[col], errors='coerce')

        result = df.groupby(['기준일ID', '행정동코드']).mean(numeric_only=True).reset_index()
        self.logger.info("데이터 전처리 완료 (숫자 변환 및 그룹바이)")
        return result

    def save(self, df: pd.DataFrame, file_name: str):
        """
        결과를 CSV 파일로 저장
        """
        output_file = os.path.join(self.processed_data_path, file_name)
        df.to_csv(output_file, index=False, encoding="utf-8")
        self.logger.info(f"전처리된 데이터 저장 완료: {output_file}")
        
class CrimRatioProcessor: 
    def __init__(self, config):
        self.processed_data_path = config["data"]["processed_data_path"]
        self.logger = setup_logger("population_processor", config["logging"]["log_file"])
        os.makedirs(self.raw_data_path, exist_ok=True)
    
    def run(crime_input_file, population_input_file, )


if __name__ == "__main__":
    import yaml
    from processors.population_processor import PopulationProcessor

    # config.yaml 파일 읽어오기
    with open("config/config.yaml", "r", encoding="utf-8") as f:
        config = yaml.safe_load(f)

    year = "2023"
    processor = PopulationProcessor(config)
    processor.run(year, f"동별생활인구수_{year}.csv")
