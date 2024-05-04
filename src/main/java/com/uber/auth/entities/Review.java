package com.uber.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Review extends CommonEntity {
    @Column
    public String content;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;
}