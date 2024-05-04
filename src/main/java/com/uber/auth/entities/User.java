package com.uber.auth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends CommonEntity {

    private String email;
    private String password;
}
