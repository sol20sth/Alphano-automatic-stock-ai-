package com.ssafy.alphano.api.redis.service.request;

import com.ssafy.alphano.domain.member.Enum.BuySell;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedisMemberRequest {

    Long memberId;

    int targetPrice;

    BuySell byeSell;

    boolean greater;

    String mktType;

    String appKey;

    String appsecret;

    String authorization;

    String CANO; // 계좌번호

    String ACNT_PRDT_CD; // 계좌번호 뒤 2자리

    String PDNO; // 종목코드

    int ORD_UNPR; // 주문가격

    int ORD_QTY; // 주문수량




    @Builder
    public RedisMemberRequest(Long memberId, int targetPrice, BuySell byeSell, boolean greater, String mktType, String appKey, String appsecret, String authorization, String CANO, String ACNT_PRDT_CD, String PDNO, int ORD_UNPR, int ORD_QTY) {
        this.memberId = memberId;
        this.targetPrice = targetPrice;
        this.byeSell = byeSell;
        this.greater = greater;
        this.mktType = mktType;
        this.appKey = appKey;
        this.appsecret = appsecret;
        this.authorization = authorization;
        this.CANO = CANO;
        this.ACNT_PRDT_CD = ACNT_PRDT_CD;
        this.PDNO = PDNO;
        this.ORD_UNPR = ORD_UNPR;
        this.ORD_QTY = ORD_QTY;
    }
}
