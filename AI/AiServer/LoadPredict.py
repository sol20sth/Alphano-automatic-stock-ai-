import FinanceDataReader as fdr
import pandas as pd
import numpy as np
from sklearn.preprocessing import MinMaxScaler
import tensorflow as tf  
from tensorflow.keras.models import model_from_json  
import os
os.chdir(os.path.dirname(os.path.abspath(__file__)))

class PredictModel:
    def __init__(self, stock_code):
        self.stock_code = stock_code
        self.formatted_values = None
        self.stock_all_data = None
    def get_stock_data(self):
        self.stock_data = fdr.DataReader(self.stock_code, start='2023', end='2024').tail(1).head(1)
        self.stock_all_data = fdr.DataReader(self.stock_code, start='2022', end='2023')
        return self.stock_data, self.stock_all_data
    
    def custom_data(self):
        print(self.stock_data, '데이터 확인해보자')
        if self.stock_code == '352820':
            self.stock_data['DPR'] = self.stock_data['High'] - self.stock_data['Low']
            self.stock_all_data['DPR'] = self.stock_all_data['High'] - self.stock_all_data['Low']
        elif self.stock_code == '329180' or self.stock_code=='034730':
            self.stock_data['OMH'] = self.stock_data['Open'] - self.stock_data['High']
            self.stock_all_data['OMH'] = self.stock_all_data['Open'] - self.stock_all_data['High']
        elif self.stock_code == '090430':
            self.stock_data['OMC'] = self.stock_data['Open'] - self.stock_data['Change']
            self.stock_all_data['OMC'] = self.stock_all_data['Open'] - self.stock_all_data['Change']
        elif self.stock_code == '035720':
            self.stock_data['IPC'] = self.stock_data['Open'] - self.stock_data['Close']
            self.stock_all_data['IPC'] = self.stock_all_data['Open'] - self.stock_all_data['Close']
        elif self.stock_code == '005830' or self.stock_code=='033780':
            self.stock_data['HMC'] = self.stock_data['High'] - self.stock_data['Close']
            self.stock_all_data['HMC'] = self.stock_all_data['High'] - self.stock_all_data['Close']
        elif self.stock_code == '316140':
            self.stock_data['LMO'] = self.stock_data['Low'] - self.stock_data['Open']
            self.stock_all_data['LMO'] = self.stock_all_data['Low'] - self.stock_all_data['Open']
        elif self.stock_code == '011170':
            self.stock_data['LMH'] = self.stock_data['Low'] - self.stock_data['High']
            self.stock_all_data['LMH'] = self.stock_all_data['Low'] - self.stock_all_data['High']
        elif self.stock_code == '030200' or self.stock_code == '017670':
            self.stock_data['LMC'] = self.stock_data['Low'] - self.stock_data['Close']
            self.stock_all_data['LMC'] = self.stock_all_data['Low'] - self.stock_all_data['Close']
        elif self.stock_code == '000810' or self.stock_code=='011200':
            self.stock_data['LMV'] = self.stock_data['Low'] - self.stock_data['Volume']
            self.stock_all_data['LMV'] = self.stock_all_data['Low'] - self.stock_all_data['Volume']
        elif self.stock_code == '018260' or self.stock_code == '068270':
            self.stock_data['CMO'] = self.stock_data['Close'] - self.stock_data['Open']
            self.stock_all_data['CMO'] = self.stock_all_data['Close'] - self.stock_all_data['Open']
        elif self.stock_code == '055550':
            self.stock_data['CMH'] = self.stock_data['Close'] - self.stock_data['High']
            self.stock_all_data['CMH'] = self.stock_all_data['Close'] - self.stock_all_data['High']
        elif self.stock_code == '024110':
            self.stock_data['CMV'] = self.stock_data['Close'] - self.stock_data['Volume']
            self.stock_all_data['CMV'] = self.stock_all_data['Close'] - self.stock_all_data['Volume']
        elif self.stock_code == '051910':
            self.stock_data['CMCh'] = self.stock_data['Close'] - self.stock_data['Change']
            self.stock_all_data['CMCh'] = self.stock_all_data['Close'] - self.stock_all_data['Change']
        elif self.stock_code == '207940' or self.stock_code=='003550':
            self.stock_data['VML'] = self.stock_data['Volume'] - self.stock_data['Low']
            self.stock_all_data['VML'] = self.stock_all_data['Volume'] - self.stock_all_data['Low']
        elif self.stock_code == '028260':
            self.stock_data['VMCh'] = self.stock_data['Volume'] - self.stock_data['Change']
            self.stock_all_data['VMCh'] = self.stock_all_data['Volume'] - self.stock_all_data['Change']
        elif self.stock_code == '086280' or self.stock_code== '009540' or self.stock_code == '402340'or self.stock_code== '012450' :
            self.stock_data['ChMO'] = self.stock_data['Change'] - self.stock_data['Open']
            self.stock_all_data['ChMO'] = self.stock_all_data['Change'] - self.stock_all_data['Open']
        elif self.stock_code == '032830':
            self.stock_data['ChMH'] = self.stock_data['Change'] - self.stock_data['High']
            self.stock_all_data['ChMH'] = self.stock_all_data['Change'] - self.stock_all_data['High']
        elif self.stock_code == '003490' or self.stock_code=='105560' or self.stock_code=='326030':
            self.stock_data['ChMC'] = self.stock_data['Change'] - self.stock_data['Close']
            self.stock_all_data['ChMC'] = self.stock_all_data['Change'] - self.stock_all_data['Close']
        elif self.stock_code == '010950':
            self.stock_data['OPC'] = self.stock_data['Open'] + self.stock_data['Close']
            self.stock_all_data['OPC'] = self.stock_all_data['Open'] + self.stock_all_data['Close']
        elif self.stock_code == '000270':
            self.stock_data['HPC'] = self.stock_data['High'] + self.stock_data['Close']
            self.stock_all_data['HPC'] = self.stock_all_data['High'] + self.stock_all_data['Close']
        elif self.stock_code == '009150':
            self.stock_data['HPCh'] = self.stock_data['High'] + self.stock_data['Change']
            self.stock_all_data['HPCh'] = self.stock_all_data['High'] + self.stock_all_data['Change']
        elif self.stock_code == '047810':
            self.stock_data['LPH'] = self.stock_data['Low'] + self.stock_data['High']
            self.stock_all_data['LPH'] = self.stock_all_data['Low'] + self.stock_all_data['High']
        elif self.stock_code == '086790' or self.stock_code=='005490':
            self.stock_data['LPC'] = self.stock_data['Low'] + self.stock_data['Close']
            self.stock_all_data['LPC'] = self.stock_all_data['Low'] + self.stock_all_data['Close']
        elif self.stock_code == '047050':
            self.stock_data['LPCh'] = self.stock_data['Low'] + self.stock_data['Change']
            self.stock_all_data['LPCh'] = self.stock_all_data['Low'] + self.stock_all_data['Change']
        elif self.stock_code == '034020':
            self.stock_data['CPH'] = self.stock_data['Close'] + self.stock_data['High']
            self.stock_all_data['CPH'] = self.stock_all_data['Close'] + self.stock_all_data['High']
        elif self.stock_code == '005930' or self.stock_code == '005935':
            self.stock_data['ODL'] = self.stock_data['Open'] / self.stock_data['Low']
            self.stock_all_data['ODL'] = self.stock_all_data['Open'] / self.stock_all_data['Low']
        elif self.stock_code == '015760':
            self.stock_data['VPL'] = self.stock_data['Volume'] + self.stock_data['Low']
            self.stock_all_data['VPL'] = self.stock_all_data['Volume'] + self.stock_all_data['Low']
        elif self.stock_code == '088980':
            self.stock_data['HDC'] = self.stock_data['High'] / self.stock_data['Close']
            self.stock_all_data['HDC'] = self.stock_all_data['High'] / self.stock_all_data['Close']
        elif self.stock_code == '012330':
            self.stock_data['VDH'] = self.stock_data['Volume'] / self.stock_data['High']
            self.stock_all_data['VDH'] = self.stock_all_data['Volume'] / self.stock_all_data['High']
        elif self.stock_code == '010140':
            self.stock_data['ChDO'] = self.stock_data['Change'] / self.stock_data['Open']
            self.stock_all_data['ChDO'] = self.stock_all_data['Change'] / self.stock_all_data['Open']
        elif self.stock_code == '005380':
            self.stock_data['ChDH'] = self.stock_data['Change'] / self.stock_data['High']
            self.stock_all_data['ChDH'] = self.stock_all_data['Change'] / self.stock_all_data['High']
        elif self.stock_code == '066570':
            self.stock_data['ChDC'] = self.stock_data['Change'] / self.stock_data['Close']
            self.stock_all_data['ChDC'] = self.stock_all_data['Change'] / self.stock_all_data['Close']
        elif self.stock_code == '010130':
            self.stock_data['OmH'] = self.stock_data['Open'] * self.stock_data['High']
            self.stock_all_data['OmH'] = self.stock_all_data['Open'] * self.stock_all_data['High']
        elif self.stock_code == '267250':
            self.stock_data['OmL'] = self.stock_data['Open'] * self.stock_data['Low']
            self.stock_all_data['OmL'] = self.stock_all_data['Open'] * self.stock_all_data['Low']
        elif self.stock_code == '028050':
            self.stock_data['LmH'] = self.stock_data['Low'] * self.stock_data['High']
            self.stock_all_data['LmH'] = self.stock_all_data['Low'] * self.stock_all_data['High']
        elif self.stock_code == '051900' or self.stock_code == '035420':
            self.stock_data['VmCh'] = self.stock_data['Volume'] * self.stock_data['Change']
            self.stock_all_data['VmCh'] = self.stock_all_data['Volume'] * self.stock_all_data['Change']

    def print_stock_data(self):
        if self.stock_data is not None:
            json_file = open("./weights/model.json", "r")
            loaded_model_json = json_file.read()
            json_file.close()
            loaded_model = model_from_json(loaded_model_json)
            scaler = MinMaxScaler()
            resultdf = pd.DataFrame(columns=['Check_Open','Check_High','Check_Low','Check_Close','Check_Volume','Check_Change'])
            
            # 저장한 가중치 불러오기
            loaded_model.load_weights(f"./weights/{self.stock_code}.h5")
            today_data = list(self.stock_data.iloc[0])
            for j in range(len(today_data)):
                if j != 5:  # 'Change' 열은 제외
                    today_data[j] = int(today_data[j])
            # 입력값을 정규화
            scaler.fit(self.stock_all_data)
            today_data_normalized = scaler.transform([today_data])  # 2D 배열로 변환
            predicted_data = loaded_model.predict(np.array([today_data_normalized]))  # 3D 배열로 변환
            predicted_data_original = scaler.inverse_transform(predicted_data.reshape(1, -1))
            # print(predicted_data_original)
            predicted_data_original = predicted_data_original[0]  # 배열의 첫 번째 요소 추출
            float_value = predicted_data_original[5]  # 종가상승률(원값 그대로 사용)
            integer_values = predicted_data_original.astype(int)  # 마지막 요소를 제외한 나머지 요소를 정수로 변환
            self.formatted_values = list(integer_values)
            self.formatted_values = self.formatted_values[1:4]
            print(self.formatted_values, '12313123')
            def listyerterday():
                # High, Low, Close 값을 추출하여 리스트에 저장
                data = self.stock_data
                print(data)
                tmp = []
                tmp.append(data['High'].values[0])
                tmp.append(data['Low'].values[0])
                tmp.append(data['Close'].values[0])
                tmp.append(data['Volume'].values[0])
                return tmp
            self.formatted_values.extend(listyerterday())
            return self.formatted_values

        else:
            print("Stock data is not available. Please call 'get_stock_data()' first.")


if __name__ == '__main__':
    stock_code = '030200'

    analyzer = PredictModel(stock_code)
    analyzer.get_stock_data()
    analyzer.custom_data()
    analyzer.print_stock_data()
    
    print(analyzer.formatted_values)
    
    