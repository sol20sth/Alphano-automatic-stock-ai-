package com.ssafy.alphano.api.member.middle.controller.request;

import com.ssafy.alphano.api.member.middle.service.dto.CreateMemberOptionDto;
import com.ssafy.alphano.api.member.middle.service.request.MemberOptionServiceRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberOptionCreateRequest {

    List<CreateMemberOptionDto> memberOptions;

    @Builder
    public MemberOptionCreateRequest(Long memberOrderStockId, Long stockOptionId, List<CreateMemberOptionDto> memberOptions) {
        this.memberOptions = memberOptions;
    }

    public MemberOptionServiceRequest toServiceRequest() {
        return MemberOptionServiceRequest.builder()
                .memberOptions(memberOptions)
                .build();
    }
}
