package com.uber.auth.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpDTO {
    private String name;
    private int age;
    private String email;
    private String password;
}
