# 💸AlphaNo

<div align="center">
<img src="./exec/readme_asset/logo.png" height="200"><br/> <span style="font-size: xx-large; justify-items: center" >주식 자동매매 서비스</span>
</div>

---

# 🎬UCC
[<img style="width: 70%;" src=".exec/readme_asset/ucc.png">]()

※ 이미지 클릭시 UCC youtube 이동

# 🤑서비스 소개
시간의 이유로 장에 집중하기 어려운 사람들을 위해 AI기반 투자 서비스를 제공<br/>
주식 시장 분석 정보와 추천 주식을 제공하여 효율적인 투자를 할 수 있도록 도움.


# 💰프로젝트 소개

- 진행 기간 : 2023.10.16 ~ 2023.11.17

### 📂 저장소

- **[🔎 Front-end 저장소](./FE)**
- **[🔎 Back-end 저장소](./BE)**
- **[🔎 AI 저장소(예측)](./AI)**

## 주요 기능

| 기능                  | 내용                                                                                                                                              |
|---------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| 자동매매 기능             | 회원들은 누구나 자신이 원하는 옵션으로 주식을 매매할 수 있습니다.<br /> - 설정한 가격에 자동으로 주식을 매도, 매수 <br /> - 설정한 필수옵션로 주식을 자동 매도, 매수 <br/>- 유저가 선택옵션으로 종목별 커스터마이징하여 매도, 매수 가능 |
| 📈AI 기능               | 회원들은 AI가 예측한 데이터를 확인하여 투자 시, 참고할 수 있습니다.<br /> - AI가 예측한 주가를 종목 조회를 통해 제공 <br /> - AI가 예측한 주가를 토대로 추천 종목을 제공<br/>- 종목별로 AI 신뢰도 제공               |
| 종목 검색 및 <br/> 종목 조회 | - 검색조건(종목 검색)에 따라 종목을 검색<br/> - WebSocket을 통해 실시간체결가 제공 <br/> - 예측 최고/최저가격을 차트로 제공 <br /> - 최근 검색 종목, 관심 종목 등록 가능                               |


## 기술 스택

### Front-end

| <img src="https://i.namu.wiki/i/d2KZFK5WdlAVl2Diq8CweJBkgCIlIJc3MybIyDTM-ZgWETJy0EEKSmxt7jjw8xIDlrgY2YhNAw8jNUzYnikImLQjoyLhOFqDMN-yYBsqBAjOkwB0nw_Mf8u7uM186Lg5-thlQwenC9Vc8aMTokb6Zg.svg" alt="React.js" width="100px" height="100px" /> | <img src="https://d33wubrfki0l68.cloudfront.net/0834d0215db51e91525a25acf97433051f280f2f/c30f5/img/redux.svg" alt="Redux" width="100px" height="100px" /> | <img src="https://vitejs.dev/logo-with-shadow.png" alt="Vite" width="100px" height="100px" /> |
|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------:|
|                                                                                                                   React                                                                                                                    |                                                                       Redux Toolkit                                                                       |                                             Vite                                              |

| <img src="https://miro.medium.com/v2/resize:fit:640/0*HdwDWjiZorOC8ZaO" alt="Socket.io" width="100px" /> | <img src="https://mui.com/static/logo.png" alt="MUI" width="100px" height="100px" /> | <img src="https://i.namu.wiki/i/Yhas-l0skAnSuvkeU-DWvJLuS5SMbdMCslwj_qB0dGxAqmSSaYRh4NshOMHuEX-sPgA5LuyqH5b56s2bSXFBcljdrwglZ4xa3ivMzZKSCUpAyfib5PPNkLhCVnN2UFXV7gqGGfGx2naf8mT73LsusQ.svg" alt="Tailwind CSS" width="100px" height="100px" /> |
|:--------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------:|
|                                    WebSocket                                     |                                         MUI                                          |                                           Tailwind CSS                                           |

### Back-end

| <img src="https://i.namu.wiki/i/MuCO_ocla-FyadGnRZytkRLggQOcqxv_hXNjN7aYXDOPivIChJNdiRXp6vwSXbM6GcUL3pVTL-5U5TKQ0f1YhA.svg" alt="HTML5" width="100px" height="100px" /> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1920px-Spring_Framework_Logo_2018.svg.png" width="100"> |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                                                  Java                                                                                   |                                                                         SpringBoot                                                                         |

| <img src="https://static-00.iconduck.com/assets.00/hibernate-icon-1965x2048-cl94vxbt.png"  alt="HTML5" width="100px" height="100px" /> | <img src="https://i.namu.wiki/i/vkGpBcmks1_NcJW0HUFa6jlwlM6h11B-8nxRRX4bYC703H4nLo7j4dQdRCC32gz8Q-BqRcAnQgFSXMjB8jPohg.svg"  alt="HTML5" width="100px" height="100px" /> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Logo-redis.svg/2560px-Logo-redis.svg.png"  alt="HTML5" width="100px" height="100px" /> |
|:--------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                               Hibernate                                                                |                                                                                  MySQL                                                                                   |                                                                           Redis                                                                            |

| <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Socket-io.svg/1200px-Socket-io.svg.png"  alt="HTML5" width="100px" height="100px" /> | <img src="https://www.thymeleaf.org/images/thymeleaf.png"  alt="HTML5" width="100px" height="100px" /> |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
|                                                                        Socket.io                                                                         |                                               Thymeleaf                                                |

### DevOps

| <img src="https://www.nginx.com/wp-content/uploads/2020/05/NGINX-product-icon.svg"  alt="HTML5" width="100px" height="100px" /> | <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Amazon_Web_Services_Logo.svg/300px-Amazon_Web_Services_Logo.svg.png"  alt="HTML5" width="100px" height="100px" /> | <img src="https://i.namu.wiki/i/08UdmuCr95pg5aRCf3wNhW4KNAFQwLpCeTwIfWkNBGBodQ8pWIGB7Sqv-180bwpfE9Aa5l9LUKNxKA3yZvHQOgWkHIb92zyHTleC35tCFWYe-irT-KqHjdzc3SuhqtZ9Wtgu8AWdGHezu5drYdihCA.svg"  alt="HTML5" width="100px" height="100px" /> | <img src="https://i.namu.wiki/i/FjgNRhrYgRD3l_QctPVceyVlYxI0Tz5GkkBraVMQm-C_8ca_zsLHeeWOEz2r3VxfSB4w8BXoHMzUqsUX340IU866OsSbq6KM3D1auKvWN8WR10eFOyE_5i3nYiOBFw5PQ9seki0hh_9bic8Ou5BTJg.webp"  alt="openVidu" width="100px" height="100px" /> |
|:-------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                              NGINX                                                              |                                                                                        aws EC2                                                                                        |                                                                                                                  Docker                                                                                                                  |                                                                                                                   jenkins                                                                                                                    |


---

### 아키텍쳐

### ERD

<img src="./exec/readme_asset/erd/erd.jpg">

### MockUp

[<img src="./exec/readme_asset/mockup/mockup.jpg">](https://www.figma.com/file/yo5GWlsCVMK321U5YvZEtQ/Untitled?type=design&node-id=0-1&mode=design&t=A89QQz4tGv8Udzg2-0)

※ 이미지 클릭시 Figma 이동


## 👨‍👩‍👧‍👦팀 소개

<table align="center">
    <tr align="center">
        <td style="min-width: 150px;">
            <a href="https://github.com/lukylun">
              <img src="./exec/readme_asset/profile/syj.png" width="200" alt="">
              <br />
              <b>lukylun</b>
            </a>
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/Raon-cs">
              <img src="./exec/readme_asset/profile/ics.png" width="200" alt="">
              <br />
              <b>Raon-cs</b>
            </a> 
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/duljji">
              <img src="./exec/readme_asset/profile/leejh.png" width="200">
              <br />
              <b>duljji</b>
            </a> 
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/sol20sth">
              <img src="./exec/readme_asset/profile/kth.png" width="200">
              <br />
              <b>sol20sth</b>
            </a> 
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/passionhuman">
              <img src="./exec/readme_asset/profile/ljh.png" width="200">
              <br />
              <b>passionhuman</b>
            </a> 
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/lukylun">
              <img src="./exec/readme_asset/profile/lgk.png" width="200">
              <br />
              <b></b>
            </a> 
        </td>
    </tr>
    <tr align="center">
        <td>
            서용준 (팀장)<br/>BE
        </td>
        <td>
            임철성 (부팀장)<br/>AI
        </td>
        <td>
            이정현<br/>BE & INFRA(BE, AI)
        </td>
        <td>
            김태형<br/>AI & BE
        </td>
        <td>
            임준환<br/>FE & INFRA(FE)
        </td>
        <td>
            이금규<br/>FE
        </td>
    </tr>
</table>

## 커밋 컨벤션

규칙 `🎉feat: S09P12C204-이슈번호 내용`

```
🎉feat: 새로운 기능을 추가할 경우
🌈style: 기능에 영향을 주지 않는 커밋, 코드 순서, CSS등의 포맷에 관한 커밋
🚑fix: 버그를 고친 경우 🚨
♻️refactor: 프로덕션 코드 리팩토링
🔨test: 테스트 코드 작성
📝docs: main 문서를 수정한 경우, 파일 삭제, 파일명 수정 등
👀code review: 코드 리뷰 반영
🏗️build: 빌드 변경
💿backup: 백업
```
