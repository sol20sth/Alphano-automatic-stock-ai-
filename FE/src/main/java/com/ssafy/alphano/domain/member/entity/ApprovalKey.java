package com.ssafy.alphano.domain.member.entity;

import com.ssafy.alphano.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
public class ApprovalKey extends BaseEntity{

    @Id
    @Column(name = "member_id")
    Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    String approvalKey;

    @Builder
    public ApprovalKey(Member member, String approvalKey) {
        this.member = member;
        this.approvalKey = approvalKey;
    }
}
