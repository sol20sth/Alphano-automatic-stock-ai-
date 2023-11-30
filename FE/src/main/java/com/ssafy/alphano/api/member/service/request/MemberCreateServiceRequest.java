package com.ssafy.alphano.api.member.service.request;

import com.ssafy.alphano.domain.member.Enum.Authority;
import com.ssafy.alphano.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Data
public class MemberCreateServiceRequest {

    String username;
    String htsId;
    String password;
    String tel;
    String appKey;
    String appsecretKey;
    String account;
    String accountCode;

    @Builder
    public MemberCreateServiceRequest(String username, String htsId, String password, String tel, String appKey, String appsecretKey, String account, String accountCode) {
        this.username = username;
        this.htsId = htsId;
        this.password = password;
        this.tel = tel;
        this.appKey = appKey;
        this.appsecretKey = appsecretKey;
        this.account = account;
        this.accountCode = accountCode;
    }

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .htsId(htsId)
                .password(password)
                .authority(Authority.ROLE_USER)
                .tel(tel)
                .appKey(appKey)
                .appsecretKey(appsecretKey)
                .account(account)
                .accountCode(accountCode)
                .build();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
