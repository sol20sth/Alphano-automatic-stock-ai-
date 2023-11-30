package com.ssafy.alphano.api.member.controller.request;

import com.ssafy.alphano.api.member.service.request.MemberCreateServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberCreateRequest {

    String username;
    String htsId;
    String password;
    String tel;
    String appKey;
    String appsecretKey;
    String account;
    String accountCode;

    @Builder
    public MemberCreateRequest(String username, String htsId, String password, String tel, String appKey, String appsecretKey, String account, String accountCode) {
        this.username = username;
        this.htsId = htsId;
        this.password = password;
        this.tel = tel;
        this.appKey = appKey;
        this.appsecretKey = appsecretKey;
        this.account = account;
        this.accountCode = accountCode;
    }

    public MemberCreateServiceRequest toServiceRequest(){
        return MemberCreateServiceRequest.builder()
                .username(username)
                .htsId(htsId)
                .password(password)
                .tel(tel)
                .appKey(appKey)
                .appsecretKey(appsecretKey)
                .account(account)
                .accountCode(accountCode)
                .build();
    }

}
