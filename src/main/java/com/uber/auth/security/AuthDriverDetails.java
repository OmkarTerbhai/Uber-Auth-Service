package com.uber.auth.security;

import com.uber.common.entities.Driver;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Class for Spring Security to manage UserDetails type class.
 * Hence, implementing the same to accommodate our class in
 * Spring Security's class type.
 */

public class AuthDriverDetails extends Driver implements UserDetails {

    Driver d;
    public AuthDriverDetails(Driver d) {
        this.d = d;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return d.getPassword();
    }

    @Override
    public String getUsername() {
        return d.getEmail();
    }
}
