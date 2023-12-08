# 💸AlphaNo

---

### 주식 자동 매수/매매 및 AI 예측값 제공 서비스

### SSAFY 9기 자율 프로젝트

## 👩‍👩‍👧‍👧 역할

---
`AI` <br/> <br/>  AI(LSTM, CNN, RNN, GRU, 앙상블) 모델을 통한 50개종목에 대한 23년 전체 6개의 예측값 생성(시가,최고값,최저가,종가,거래량,변동률) 및 일일단위 예측값 생성<br/>
       모델에 맞는 터이터 전처리 작업<br/>
       특성열 변환 및 최저화 모델 찾기
<br/>

`FE` <br/> <br/>  기획 및 Figma작업 <br/> 
       스탑로스, AI, 잔고, 홈(최신, 전체)페이지 제작<br/>
       Timer, NumberFormat, 주가 단위 hook제작<br/>
       PWA적용

       
<br/>

## 🔗 프로젝트 개요

---

### 💡 기획 배경
 주식 매수/매매를 통한 여유자금 확보를 위한 사람들이 많이 존재한다는 것을 알 수 있다.
 하지만 주식의 장 같은 경우 09~16시까지 진행되다보니 많은 회사원 또는 별도의 업무를 진행하는 사람들은 매 시간동안 주식장을 확인하는게 제한된다는 점이 존재하면 이런 이들을 위한 주식 자동매수/매매 기능을 제공하며, 주식에 대한 기본적인 정보가 없이 단순감으로만 진행하는 이들이 많은데 이들은 별도의 정보가 없이 주식을 매수/매매 하기 때문에 이득을 보기 힘들기 때문에 이런이들을 위하여 종목별 주식 최고가,최저가를 현재 호가그래프와 함께 제공하여 사용자들이 쉽게 다음날의 최고가와 최저가를 예측하여 제공하여 본인들이 판단하기 위한 기본 요건을 제공하며 일일단위 예측값의 정확도를 실제값과 비교하여 정확도를 별도로 제공하여 사용자들이 해당 서비스를 통해 정확도가 높은 주식들에 대해서만 주식 매수,매매를 진행할 수 있으며 50개종목에 대한 최저값 신뢰도, 최고값 신뢰도, 종가신뢰도등 별개의 신뢰도값을 기준으로 정렬하여 원하는 예측값의 신뢰도를 확인하여 쉽게 매수,매도를할 수 있게 하기 위하여 **쉬운 주식 자동 매수매도**를 할수 있게 서비스를 고안하였다.

### 📅 프로젝트 진행 기간

2023.10.16 ~ 2023.11.17

## 🖇️ 주요 기능

---

### 🎲 자동 매수,매매 기능

- 회원들은 누구나 자신이 원하는 옵션으로 주식을 매매할 수 있습니다.
- 사용자가 가격, 필수 및 선택옵션을 설정하여 자동으로 주식을 매도, 매수 할 수 있다.
- 사용자가 선택한 옵션에 대하여 진행중, 완료 여부를 제공하여 실시간으로 사용자들이 쉽게 확인할 수 있다.
- 옵션에 대한 기본적인 이해도가 부족한 사용자들을 위하여 옵션별 기본적인 설명을 추가하였다.

### 🎲 AI예측값 제공

- 회원들은 AI가 예측한 데이터를 확인하여 투자 시, 참고할 수 있습니다.
- AI가 예측한 최고값, 최저값을 종목별 현재 호가 그래프와 함께 제공하여 사용자들이 쉽게 확인할 수 있다.
- 메인탭을 통해 현재 신뢰도 상위 5개 종목에 대한 정보를 실시간으로 확인할 수 있다.
- AI신뢰도 탭을 통해 일주일 신뢰도, 최고값 신뢰도, 최저값 신뢰도등 사용자의 취향에 맞게 신뢰도를 정렬하여 확인이 가능하다.
- AI신뢰도 탭에서 각 종목별 최고 신뢰도, 최저 신뢰도를 별개의 색상으로 제공하여 사용자들이 쉽게 확인이 가능하다.

### 🎲 종목 검색 및 종목 조회

- 종목명 또는 종목코드를 통하여 사용자가 원하는 종목을 검색할 수 있다.
- 자동완성기능을 통해 사용자가 검색한 종목명 또는 종목코드에 따라 아래에 검색가능한 종목을 제공한다.
- 해당 종목을 선택 시 종목에 대한 일일호가 정보 및 AI예측 최고가,최저값을 그래프를 통해 제공하여 사용자가 쉽게 이해가 가능하다.
- 최근 검색 종목 5개에 대한 저장 기능제공 및 사용자가 관심목록을 선택할 경우 해당 관심종목에 대한 정보를 저장하여 사용자가 추후 쉽게 검색이 가능하다.

### 🎲 개인정보 페이지

- 개인정보 페이지를 통하여 사용자의 현재 보유종목, 보유 자산, 진행중인 옵션등에 대한 내용을 쉽게 확인이 가능하다.


## 🏹 시스템 아키텍처
---
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/60f739b7-36dd-477e-a1bc-8ce22d4e94ff" width="80%"/>

## ⚒️ 개발 및 협업 환경

---

### **AI***

- LSTM, GRU, RNN, CNN
- FinanceDataReader
- Pandas
- Numpy
- MinMaxScaler
- Keras
- Tensorflow

### **FrontEnd**

- React
- Redux-toolkit
- Mui-material
- Vite
- WebSocket
- PWA
- Tailwind Css

### **BackEnd**

- Java
- SpringBoot
- Hibernate
- MySQL
- Redis
- Socket.io
- Thymeleaf


### Cooperation & Communication

- GitLab
- Jira
- MatterMost
- Notion
- Discord

## 💻 서비스 화면

---

### 메인화면
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/804e06c6-f5b5-4af4-9ec5-df66b41c0a54" width="30%"/>

### 로그인 / 회원가입
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/c18168f6-3c74-4d74-bb95-fd797c6dc0cc" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/34c5e6db-3f1b-4886-8cf2-9e19feae7ea3" width="30%"/>

### 홈화면
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/0515e84f-c0e3-4595-86d8-d2e2caa9f787" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/d140454f-020a-4cee-9bd5-ee9ddd1ceda0" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/5c704c1d-76c9-453f-9763-8e81586bb67d" width="30%"/>

### 종목상세 / 개별 종목 호가창
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/7ffcee02-911a-47d8-be8b-974a0fb381c6" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/b5a44b03-2c0d-4f45-b371-ce3f34a20c60" width="30%"/>

### 매수감시 옵션설정
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/01a10052-70da-4462-835a-7f66c65d906f" width="30%"/>

### 매수감시 옵션설명
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/ec549a57-2e7c-41bc-94d7-b69b534ed7b3" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/59b9f638-a6b0-42e6-8274-318c92fbf257" width="30%"/>

### 주식 보유상태시 매도옵션설정기본창
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/5328a99d-94c3-4e6e-a2e0-9b35e51db887" width="30%"/>

### 매도옵션 설정
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/c48c59f5-21e7-49d6-8de4-c0cefdf19e49" width="30%"/>

### 자동매매 옵션내역
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/373ffa51-1352-43d8-b9e1-cebefdf24eb0" width="30%"/>

### AI분석 메인
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/74f58070-cbf5-4cbf-a6f4-7612f954e5c9" width="30%"/>

### AI분석 신뢰도 설명
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/1f898575-cf52-44ac-a693-c2cf379d788b" width="30%"/>

### AI분석 종가단위 정렬
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/553f9e9c-01e6-402e-9d89-2b97c98cf1fd" width="30%"/>

### 잔고탭
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/21d4d59c-eb16-4fbc-9531-852383240c6b" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/a6f4bf6e-726b-4236-927c-c9ca1182c2fd" width="30%"/>``


### 자산상세보기 / 예수금
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/9760ec15-755a-4195-86b8-46f432648085" width="30%"/>
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/2a024dcd-9bf9-42f0-aec9-058b985c3355" width="30%"/>

## 🖥️ 화면 설계서(Figma)

---
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/0217dfdd-8ef1-40ce-b449-953710fbddb9" width="80%"/>

## ⚙️ ERD

---
<img src="https://github.com/Raon-cs/stock_automatic_AIrecommend/assets/108639467/c97b4b25-eade-4f40-9912-d5a614f72296" width="80%"/>