package com.uber.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @PrePersist
    public void beforeSave() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedAt = new Date();
    }
}