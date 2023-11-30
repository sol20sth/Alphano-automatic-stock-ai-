package com.ssafy.alphano.api.member.service.response;

import com.ssafy.alphano.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberCreateServiceResponse {

    Long id;

    String username;

    @Builder
    public MemberCreateServiceResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static MemberCreateServiceResponse of(Member createdMember) {
        return MemberCreateServiceResponse.builder()
                .id(createdMember.getId())
                .username(createdMember.getUsername())
                .build();
    }
}
