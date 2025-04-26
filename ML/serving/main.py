from fastapi import FastAPI
from endpoints.predict_price import router as price_router
from endpoints.detect_fraud import router as fraud_router

app = FastAPI()
app.include_router(price_router)
app.include_router(fraud_router)
