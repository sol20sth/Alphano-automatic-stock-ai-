<h1>딥러닝 모델</h1>

1. RNN (Recurrent Neural Network):

    순차적인 데이터, 시계열 데이터 또는 자연어 처리와 같은 문제에 효과적인 모델
    RNN은 순환 구조로, 이전 스텝의 출력을 현재 스텝의 입력과 함께 처리
    RNN은 시간에 따른 패턴을 학습할 수 있지만, 장기 의존성 문제가 있어 긴 시퀀스에서는 성능이 저하 가능성 높음
    RNN의 문제점을 해소하기 위해 만들어진 모델은 LSTM이 있음

    RNN학습결과 : 여러개의 주식 데이터를 glob을 통해서 합쳐서 넣었을때 과접합 문제와 데이터가 값이 튀는 문제가 발생하여서 학습하지 않은 테스트 케이스에서 오차율이 20%가 되는 문제가 발생


2. CNN (Convolutional Neural Network):

    CNN은 이미지 분류와 컴퓨터 비전과 관련된 작업에서 주로 사용되는 모델
    CNN은 합성곱 층을 사용하여 이미지의 지역 패턴을 추출하고, 풀링 층을 사용하여 공간 해상도를 줄인다.
    CNN은 특징 추출과 분류를 위한 다양한 층을 포함하며, 이미지 처리에 강점을 가지고 있음

    CNN : 많은 데이터를 학습해야 하는 상황에서 예외처리가 원할하지 않으며 순차적인 데이터 처리에는 좋지 않다고 알고 있어 생략 

3. LSTM (Long Short-Term Memory):

    LSTM은 RNN의 한 종류로, RNN의 단기 메모리 문제를 해결하기 위해 개발
    LSTM은 긴 시퀀스에서 장기 의존성을 학습할 수 있으며, 게이트 메커니즘을 사용하여 정보를 보존 또는 삭제
    (input gate, forget gate, output gate)
    자연어 처리와 시계열 데이터 분석에 사용

    LSTM 학습결과: GRU, LSTM_GRU앙상블 모델에 비해 오차율이 높은 편이였으며 많은 데이터를 학습시키는데 시간이 GRU모델에 비해서 1.7배이상 났음
    적은 데이터를 학습시킬때는 계속 체크포인트를 update하면서 효율적으로 변하는 모습이 보였지만 
    50000row이상의 데이터를 학습시킬때는 EarlyStopping에 계속걸리면서 높은 오차율이 나오는 모습이 보여서 생략

4. GRU (Gated Recurrent Unit):

    GRU는 LSTM모델의 구조를 더욱 간단하게 개선한 모델
    GRU는 LSTM보다 간단하며 일부 상태 정보를 보존하고 다른 정보를 삭제하는 게이트를 사용 (reset gate, update gate)
    LSTM과 비교하여 더 빠르게 학습하고 적은 매개변수를 가질 수 있다.
    LSTM과 비교하여 과적합 관리 용의

    GRU 학습결과 : 오차율이 가장 낮은 편이었으며 특히 각 종목별로 학습을 시키는 장기의존성이 적은 데이터를 학습시켰을때 가장 효율적이었음
    많은 종목, epoch의 수를 늘려서 학습을 시켜야하는 상황에서 가장 시간적인 측면에서 효율적이었기 때문에 채택
    
5. 앙상블 모델 (Ensemble Model):

    앙상블 모델은 여러 다른 모델의 예측을 결합하여 만드는 모델
    LSTM, GRU모델을 합쳐서 모델 생성
    앙상블 모델은 과적합을 줄이고 모델의 일반화 성능을 향상시키는 데 효과적입니다.

    앙상블 모델 학습결과 : GRU모델과 비슷한 오차율을 보이며 성능이 좋았지만 시간이 많이 걸림


<h1>데이터 저장</h1>

1. 코스피 50에 해당하는 종목 데이터 finance-datareader라이브러리를 통해서 받아오기
   row = '날짜데이터', col = ['Clos', 'Open', 'High', 'Low', 'Vol', 'Change']
2. 해당 데이터에 0, NaN값이 포함된 해당 row삭제
3. 각 종목별 데이터에 
   Date,Open,High,Low,Close,Volume,Change,ma5,ma10,ma20,S&P500,rsi,%K,%D,bb_upper,bb_sma,bb_lower,volume_ma5,momentum,high_low_diff
   해당 데이터를 넣어서 저장 
   - Date를 col에 추가 (데이터를 사용할때 row는 index로 설정하고 사용하기 위해서) 
   - kospi데이터를 받아와서 해당 row==날짜와 같은곳에 kosip지수 넣기
   - 나머지 추가된 변수들 메서드 추가
   - ma5 => rolling(window=5).mean()  : rolling 윈도위 크기에 따른 이동평균, 이동평균 합계 구한 후, mean으로 평균을 구하기 => 5일 평균선
   - 블린저 함수 위, 아래 => 블린저 함수는 꺽이는 부분이 중요하기 때문에 블린저 함수의 위, 아래만 저장

4. 보조지표
   - 'ma20' (이동평균 20일): 최근 20일 동안의 주식 종가의 평균값을 계산한 지표
   - 'S&P500' (S&P 500 지수): 미국 주식 시장의 대표 지수
   - 'Kospi' (KOSPI 지수): 한국거래소에 상장된 회사의 주식 가격을 종합한 지수
   - 'rsi' (상대강도지수, Relative Strength Index): 주식의 상대적인 강도와 약점을 나타내는 지표로, 일반적으로 14일 동안의 가격 상승과 하락을 기반으로 계산
   - '%K' (스토캐스틱 %K): 주식 가격의 현재 상대적 위치를 나타내는 지표로, 주식 가격의 최고점과 최저점 사이의 위치를 백분율로 표현
   - '%D' (스토캐스틱 %D): 스토캐스틱 %K 값의 이동평균을 나타내는 지표
   - 'bb_upper' (볼린저 밴드 상단): 주식 가격의 상한선을 나타내는 지표 , 중심 밴드에서 표준편차의 여러 배수(일반적으로 2배수)를 더한 값
   - 'bb_lower' (볼린저 밴드 하단): 주식 가격의 하한선을 나타내는 지표, 중심 밴드에서 표준편차의 여러 배수(일반적으로 2배수)를 뺀 값
   - 'volume_ma5' (5일 이동평균 거래량): 최근 5일 동안의 거래량의 평균값을 계산한 지표
   - 'momentum' (모멘텀): 주식 가격의 현재 상승 또는 하락 추세를 나타내는 지표, 특정 기간 동안의 가격 차이를 계산
   - 'high_low_diff' (고가 - 저가 차이): 주식의 고가와 저가 사이의 차이를 나타내는 지표, 주식의 변동성을 표현

<h1>학습방법</h1>

MinMaxScaler()를 통해 데이터 정규화 후 학습

1. 모든 종목의 데이터를 통으로 학습
    모든종목의 데이터를 glob을 사용하여 불러온 후 하나의 데이터 셋으로 설정
    'Close', 'Open', 'High', 'Low', 'Vol' 기본으로 사용할 컬럼으로 생각
    나머지 추가 보조지표들 combination을 통해서 각각의 조합을 만들어서 해당 보조지표들을 사용하여 학습
    보조지표, window_size, optimizer=Adam(learning_rate)등을 변경하며 가장 좋은 값을 만드는 컬럼조합등을 만들기

    M1 = 'ma10'
    DU = 'high_low_diff bb_upper'
    M1SP = 'ma10 S&P500'
    M2VM = 'ma20 volumn_ma5'
    SPU = 'S&P500 bb_upper'
    M1K = 'ma10 %K'
    M2SP = 'ma20 S&P500'
    KOU = 'Kospi bb_upper'
    colset_list = [M1, DU, M1SP, SPU, M1K, M2SP, KOU]

    가장 예측값이 정확한 7개의 조합들을 가지고 각 종목을 예측시켜봄
    종목들마다 예측 편차가 많이 나는 문제가 발생

2. 각 종목의 데이터만으로 학습
    각 종목의 데이터를 반목문을 통해서 '{종목코드}.csv'파일로 저장
    반복문을 돌려 file_path에 해당 종목코드가 포함된 파일을 찾아 stock변수로 설정
    각 종목마다 colset_list = [M1, DU, M1SP, SPU, M1K, M2SP, KOU] 모두 학습시키고 각각 체크포인트 저장하기 
    (checkpoint => filepath=f'./checkpoint/GRU/{stock_num}_{tmp}_{WINDOW_SIZE}_{learning_rate}.h5')
verbose=1를 사용하여서 학습하는 동안 학습 결과값을 대충 예상


<h1>예측</h1>

1. 예측 데이터에 해당된 체크포인트를 가져와서 예측

    model = load_model(f'./checkpoint/GRU/{stock_num}_{tmp}_{WINDOW_SIZE}_{learning_rate}.h5', custom_objects={'MeanAbsoluteError': MeanAbsoluteError()})
    loss, mse의 예측값 확인
    예측할 데이터 셋을 모델에 맞게 3D형식으로 설정(LSTM, GRU, LSTM_GRU앙상블)
    (input데이터 설정, output데이터 설정) => outputdata : 예측결과값과 비교하기 위해서
    예측결과는 정규화된 데이터 형식이기 때문에 다시 원래상태로 변경 => scaler.inverse_transform(predicted_value)
    예측결과중 'Close', 'High', 'LOw'만 필요하기 때문에 해당 컬럼만 포함해서 데이터리스트 생성
    예측결과와 비교하기 위해 원래 데이터를 '_Close', '_High', '_Low'컬럼을 생성 후 위의 데이터리스트에 추가
    사용한 컬럼을 확인하기 위해 'Use_Cols_Check' 컬럼을 추가해 사용한 use_cols들을 join후 데이터 추가
    
    완료된 데이터 엑셀파일로 만든 후 예측된 값의 %구하기

<h1>Loss</h1>

- Huber Loss (후버 손실):
  통 데이터를 설정하여서 학습을 시켰기 때문에 각종목의 데이터가 합쳐질때 이상치가 설정된다고 생각하였기 때문에 loss로 mse보다는
  Huber 사용하여 loss처리 하였다.

  1. Huber 손실은 MSE와 절대값 손실 (Absolute Error Loss) 사이의 절충안으로 볼 수 있다.
  2. Huber 손실은 주로 이상치에 민감하지 않은 손실 함수로 알려져 있다. MSE에 비해 이상치에 민감하지 않다.
  손실은 오차가 작을 때는 평균 제곱 오차(MSE)와 유사하게 동작하지만, 오차가 클 때는 절대값 손실과 유사하게 동작합니다.
  Huber 손실의 수식:
  오차(e)가 delta 이하일 때: 0.5 * e^2
  오차(e)가 delta를 초과할 때: delta * (|e| - 0.5 * delta)
  
- Mean Squared Error (MSE) Loss (평균 제곱 오차 손실):

  1. 예측값과 실제값 사이의 오차의 제곱을 계산하고 평균
  2. MSE는 큰 오차에 민감하게 반응하며, 이상치가 있는 경우 모델이 이상치에 크게 영향을 받을 수 있다.
  3. MSE 손실의 수식:
      0.5 * (y_pred - y_true)^2

<h1>Optimizer </h1>

1. Adam
  - 일반적인 가중치 감쇄 방법을 사용
  - 이전 그레이디언트의 지수 이동 평균을 사용하여 학습률을 조절하며, 빠르게 수렴하고 높은 학습률을 사용할 수 있다.
  optimizer로 선택

2. AdamW
  - Adam의 변형 알고리즘으로 가중치 감쇠(Weight Decay)를 좀 더 효과적으로 다루는 데 중점을 둡니다.
  - 모델의 과적합을 줄이는 데 효율적
    - learning_rate=learning_rate, #  가중치 및 편향(parameter)을 조정하는 데 사용되는 스칼라 값
    - weight_decay=0.004,  # 가중치 감쇠
    - epsilon=1e-07, # 0으로 나누지 않아지도록 매우 작은 값 설정
    - amsgrad=False,
    - clipnorm=None,  # 가중치의 기울기는 해당 노름이 이 값보다 높지 않도록 개별적으로 잘린다
    - clipvalue=None, # 부동. 설정된 경우 각 가중치의 기울기가 이 값보다 높지 않도록 잘린다
    - global_clipnorm=None,
    - use_ema=False, # EMA는 모델 가중치의 지수 이동 평균(각 훈련 배치 후 가중치 값이 변경됨)을 계산하고 주기적으로 이동 평균으로 가중치를 덮어쓰는   것으로 구성
    - ema_momentum=0.99, # 모델 가중치의 EMA를 계산할 때 사용할 운동량
      (new_average = ema_momentum * old_average + (1 - ema_momentum) * current_variable_value)

    사용결과 : 과접합을 줄이는데 효율적인 optimizer인데 학습 초기에는 많은 양의 데이터를 학습시키기 보다는 여러 차이를 줘서 어떤 열, 어떤 방식등이 
    효율적인지 먼저 판단을 해야했기 때문에 Adam보다 낮은 예측값이 나와서 Adam으로 채택  

3. learning_rate 
  -  모델이 각 반복(에포크)에서 가중치를 업데이트할 때 얼마나 큰 보폭으로 이동할지 결정
  1) 학습률 스케줄링 - 초기에는 큰 learning_rate를 사용하여 빠르게 수렴하도록 하고, 학습이 진행됨에 따라 learning_rate를 줄여가는 방식을 사용
     - window_size, epoch, patience, 특성열 등 다양한 값들을 변경하면서 해야하기 때문에 같은 조건에서 learning_rate를 줄여가며 학습시키기는 어렵다고 
     판단하였음 
  2) 그리드 서치 - 여러 가지 learning_rate 값을 시도해보고 검증 데이터를 통해 모델의 성능을 평가하여 가장 적절한 learning_rate를 선택
     - 그래서 같은 조건에서 다른 learning_rate를 넣어서 학습시킨 후 가장 효율적이라고 판단한 learning_rate를 선택 후 학습 진행

<h1>FastAPI 예측 모델 확인</h1>
  
  - Class형식으로 전날데이터, 전체데이터를 받아와서 예측 Class생성
  1) 처음에는 예측해야할 데이터만 받아와서 정규화진행 후 모델, 가중치 불러온 후 
  예측 진행하였음 --> 문제 해당날의 데이터를 거의 그대로 예측하는 문제가 생김
  2) 데이터를 정규화 할떄를 생각해보니 전체의 데이터를 넣지 않은 상태로 하나의 데이터 셋만을 넣어주니 해당 정규화 값이 그 하나의 데이터를 가지고 정규화를 진행 : 거의 동일한 값의 예측결과 나옴
  3) 해당 문제를 해결하기 위해 전체 데이터를 받아와서 그 값을 기준으로 예측해야할 데이터를 정규화 진행 후 예측 실행  


<h1>GPU서버 conda로 가상환경 구축</h1>


conda --version : 콘다 버전 확인
conda create -n 가상환경이름 python=3.7  : 파이썬 3.7 가상환경 만들기
conda activate 가상환경이름  : 가상환경 활성화
conda deactivate : 가상환경 비활성화

jupyter notebook kernel 추가해서 사용하기

jupyter kernelspec list : 커널 리스트 확인
pip install ipykernel : ipykernel 설치
python -m ipykernel install --user --name 가상환경이름 --display-name 출력명 : 가상환경이름, 보여질 가상환경명 설정 

텐서플로-gpu , cuDNN, CUDA 환경 설치

https://www.tensorflow.org/install/source_windows?hl=ko#tested_build_configurations
에서 tensorflow-gpu 버전, cuDNN, 쿠다 버전, python버전확인

pip install tensorflow-gpu==2.10.0 
conda install -c anaconda cudatoolkit=11.2
conda install -c anaconda cudnn=8.1.0

nvcc --version  //CUDA Toolkit의 버전확인

nvidia-smi // gpu서버 현황 확인

python // 파이썬 실행
import tensorflow as tf
print(tf.__version__)
tf.test.is_built_with_cuda() // CUDA로 빌드되는지 확인
tf.test.is_built_with_gpu_support()  //CUDA와 같은 GPU로 빌드되는지 확인
tf.test.gpu_device_name() // 사용가능한 GPU기기 출력


가상환경
nano ~/.bashrc  

export LD_LIBRARY_PATH=/usr/local/cuda/lib64:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=/usr/local/cuda/extras/CUPTI/lib64:$LD_LIBRARY_PATH
export LD_LIBRARY_PATH=/usr/local/cuda/include:$LD_LIBRARY_PATH

ctrl + o 저장 ctrl + x 종료
source ~/.bashrc // 저장
echo $LD_LIBRARY_PATH // 확인

추가 설치 라이브러리
sklearn : 딥러닝 모델 학습 , 예측을 위한 라이브러리
pandas : 자료구조 및 데이터 조작 및 분석 
matplotlib : 데이터 시각화 하기 위한 라이브러리
seaborn : 데이터 시각화 라이브러리

