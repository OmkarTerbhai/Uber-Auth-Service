package com.uber.auth.dto;

import com.uber.auth.entities.Review;
import com.uber.auth.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SecondaryTable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DriverDTO extends User {
    private String name;
    private int age;
    private String email;
    private String password;
}
