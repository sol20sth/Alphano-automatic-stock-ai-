package com.ssafy.alphano.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
