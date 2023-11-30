package com.ssafy.alphano.api.member.service.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalKeyResponse {

    String username;
    String approvalKey;

    LocalDateTime updatedTime;

    @Builder
    public ApprovalKeyResponse(String username, String approvalKey, LocalDateTime updatedTime) {
        this.username = username;
        this.approvalKey = approvalKey;
        this.updatedTime = updatedTime;
    }
}
