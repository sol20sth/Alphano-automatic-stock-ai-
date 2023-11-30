package com.ssafy.alphano.domain.stockoption.entity;

import com.ssafy.alphano.api.member.middle.service.dto.UpdateMemberOptionDto;
import com.ssafy.alphano.domain.common.BaseEntity;
import com.ssafy.alphano.domain.member.entity.middle.MemberOption;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@RequiredArgsConstructor
public class StockOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_option_id")
    Long id;

    @Column(name = "option_name", columnDefinition = "varchar(20)", unique = true)
    String optionName;

    @Builder
    public StockOption(Long id, String optionName) {
        this.id = id;
        this.optionName = optionName;
    };

}
