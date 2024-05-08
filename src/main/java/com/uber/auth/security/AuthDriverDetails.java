package com.uber.auth.security;

import com.uber.common.entities.Driver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Class for Spring Security to manage UserDetails type class.
 * Hence, implementing the same to accommodate our class in
 * Spring Security's class type.
 */
public class AuthDriverDetails extends Driver implements UserDetails {

    public AuthDriverDetails(Driver d) {
        this.setEmail(d.getEmail());
        this.setPassword(d.getPassword());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }


}
