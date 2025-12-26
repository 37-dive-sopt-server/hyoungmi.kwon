package org.sopt.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseSoftDeleteEntity extends BaseEntity {

    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.isDeleted = true;
    }
}