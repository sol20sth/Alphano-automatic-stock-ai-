package com.ssafy.alphano.api.member.middle.service.request;

import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberOptionServiceRequest {

    List<CreateMemberOptionDto> memberOptions;

    @Builder
    public MemberOptionServiceRequest(List<CreateMemberOptionDto> memberOptions) {
        this.memberOptions = memberOptions;
    }

    public MemberOptionServiceRequest toServiceRequest() {
        return MemberOptionServiceRequest.builder()
              .memberOptions(memberOptions)
              .build();
    }
}
