package com.uber.auth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rider extends CommonEntity {
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "rider")
    private List<Review> riderReviews;
}