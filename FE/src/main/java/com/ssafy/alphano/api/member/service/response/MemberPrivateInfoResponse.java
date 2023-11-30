package com.ssafy.alphano.api.member.service.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberPrivateInfoResponse {

    String authorization;

    String approvalKey;

    String appkey;

    String appsecret;

    String CANO;

    String ACNT_PRDT_CD;

    public MemberPrivateInfoResponse(String authorization, String approvalKey, String appkey, String appsecret, String CANO, String ACNT_PRDT_CD) {
        this.authorization = authorization;
        this.approvalKey = approvalKey;
        this.appkey = appkey;
        this.appsecret = appsecret;
        this.CANO = CANO;
        this.ACNT_PRDT_CD = ACNT_PRDT_CD;
    }
}
