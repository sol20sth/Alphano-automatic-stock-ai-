import os
import sys
from typing import Union
from fastapi import FastAPI, Request
sys.path.append(os.path.abspath('C:/Users/SSAFY/Desktop/finalProject/S09P31C106/AI/CS'))
from LoadPredict import PredictModel
from datetime import datetime
import pandas as pd
from ta.momentum import RSIIndicator
from ta.trend import MACD


app = FastAPI()

@app.post("/api/v1/ai/stock-code")
async def get_stock_codes(stock_codes: list[str]):
    
    stock_data = {}

    for stock_code in stock_codes :
        analyzer = PredictModel(stock_code)
        analyzer.get_stock_data()
        analyzer.custom_data()
        current_stock_data = {}

        for idx, predict_value in enumerate(analyzer.print_stock_data()) :
            print(str(predict_value))
            if idx == 0 :
                current_stock_data['max_price'] = str(predict_value)
            elif idx == 1 :
                current_stock_data['min_price'] = str(predict_value)
            elif idx == 2 :
                current_stock_data['final_price'] = str(predict_value)
            
            stock_data[stock_code] = current_stock_data
    print(stock_data)
    return {"data" : stock_data}


@app.post("/api/v1/ai/get-rsi")
async def get_rsi(stock_price : list[int]):
    data = {}
    data['price'] = stock_price
    df = pd.DataFrame(data)
    rsi_indicator = RSIIndicator(close=df['price'], window=14)
    df['rsi'] = rsi_indicator.rsi()
    rsi = df['rsi'].iloc[-1]
    return {"rsi" : rsi}

@app.post("/api/v1/ai/get-macd")
async def get_macd(stock_price : list[int]):
   # MACD 계산
    data = {}
    data['price'] = stock_price
    df = pd.DataFrame(data)
    macd_indicator = MACD(close=df['price'], window_slow=26, window_fast=12, window_sign=9)
    df['macd'] = macd_indicator.macd()
    df['signal_line'] = macd_indicator.macd_signal()
    
    macd = df['macd'].iloc[-1]
    
    return {"macd" : macd}