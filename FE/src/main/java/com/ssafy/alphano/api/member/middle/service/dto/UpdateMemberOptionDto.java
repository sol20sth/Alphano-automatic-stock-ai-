package com.ssafy.alphano.api.member.middle.service.dto;

import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import lombok.Builder;
import lombok.Data;

@Data
public class UpdateMemberOptionDto {

    private float optionValue;

    private OptionInequality optionInequality;

    private String optionName;

    @Builder
    private UpdateMemberOptionDto (float optionValue, OptionInequality optionInequality, String optionName) {
        this.optionValue = optionValue;
        this.optionInequality = optionInequality;
        this.optionName = optionName;
    }
}
