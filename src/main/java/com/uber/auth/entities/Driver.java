package com.uber.auth.entities;

import com.uber.auth.entities.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.uber.common.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends CommonEntity{
    private String name;
    private int age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.PERSIST)
    private List<Review> driverReviews;
}