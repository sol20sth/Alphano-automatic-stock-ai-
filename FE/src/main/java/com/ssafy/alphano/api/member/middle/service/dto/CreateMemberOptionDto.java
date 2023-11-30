package com.ssafy.alphano.api.member.middle.service.dto;

import com.ssafy.alphano.domain.member.Enum.OptionInequality;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberOptionDto {

    private float optionValue;

    private OptionInequality optionInequality;

    private String optionName;

    @Builder
    public CreateMemberOptionDto(float optionValue, OptionInequality optionInequality, String optionName) {
        this.optionValue = optionValue;
        this.optionInequality = optionInequality;
        this.optionName = optionName;
    }
}

