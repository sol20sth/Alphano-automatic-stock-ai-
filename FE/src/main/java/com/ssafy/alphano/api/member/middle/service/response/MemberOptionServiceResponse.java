package com.ssafy.alphano.api.member.middle.service.response;

import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import lombok.Builder;

public class MemberOptionServiceResponse {

    Long id;

    float optionValue;

    OptionInequality optionInequality;

    @Builder
    public MemberOptionServiceResponse(Long id, float optionValue, OptionInequality optionInequality) {
        this.id = id;
        this.optionValue = optionValue;
        this.optionInequality = optionInequality;
    }
}