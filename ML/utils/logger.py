# utils/logger.py

import logging
import os

def setup_logger(name: str, log_file: str, level=logging.INFO, format_string=None):
    """로그 파일 및 콘솔 출력을 설정합니다."""
    formatter = logging.Formatter(
        format_string or "[%(asctime)s] %(levelname)s - %(message)s"
    )
    handler = logging.FileHandler(log_file)
    handler.setFormatter(formatter)

    console = logging.StreamHandler()
    console.setFormatter(formatter)

    logger = logging.getLogger(name)
    logger.setLevel(level)
    logger.addHandler(handler)
    logger.addHandler(console)

    return logger
