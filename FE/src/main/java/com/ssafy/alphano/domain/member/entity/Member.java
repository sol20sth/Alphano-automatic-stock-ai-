package com.ssafy.alphano.domain.member.entity;

import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.Enum.Authority;
import com.ssafy.alphano.domain.member.entity.middle.MemberLikeStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberRecentStock;
import com.ssafy.alphano.domain.member.entity.middle.MemberStock;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    Long id;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false, unique = true)
    String htsId;

    @Column(name = "encrypted_password", nullable = false)
    String password;

    @Column
    String tel;

    @Column(nullable = false)
    String account; // 계좌 앞 8자리

    @Column(nullable = false)
    String accountCode; // 계좌 뒤 2자리

    @Column(nullable = false)
    String appKey;

    @Column(columnDefinition = "TEXT", nullable = false)
    String appsecretKey;

    @Column(columnDefinition = "TEXT")
    String accessToken;

    @Column(columnDefinition = "TEXT")
    String approvalKey;

    @Enumerated(EnumType.STRING)
    Authority authority = Authority.ROLE_USER;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberStock> memberStocks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberLikeStock> memberLikeStocks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = LAZY)
    private List<MemberRecentStock> memberRecentStocks = new ArrayList<>();

    @Builder
    public Member(Long id, String username, String htsId, String password, String tel, String account, String accountCode, String appKey, String appsecretKey, String accessToken, String approvalKey, Authority authority, List<MemberStock> memberStocks, List<MemberLikeStock> memberLikeStocks, List<MemberRecentStock> memberRecentStocks) {
        this.id = id;
        this.username = username;
        this.htsId = htsId;
        this.password = password;
        this.tel = tel;
        this.account = account;
        this.accountCode = accountCode;
        this.appKey = appKey;
        this.appsecretKey = appsecretKey;
        this.accessToken = accessToken;
        this.approvalKey = approvalKey;
        this.authority = authority;
        this.memberStocks = memberStocks;
        this.memberLikeStocks = memberLikeStocks;
        this.memberRecentStocks = memberRecentStocks;
    }

    public Authority getAuthority(){
        return this.authority;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateApprovalKey(String approvalKey) {
        this.approvalKey = approvalKey;
    }
}
