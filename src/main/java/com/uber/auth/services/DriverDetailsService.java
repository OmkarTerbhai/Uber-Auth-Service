package com.uber.auth.services;

import com.uber.auth.entities.Driver;
import com.uber.auth.repositories.DriverRepository;
import com.uber.auth.security.AuthDriverDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverDetailsService implements UserDetailsService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Driver> driver = this.driverRepository.findByEmail(username);

        if(driver.isPresent()) {
            return new AuthDriverDetails(driver.get());
        }
        else {
            throw new UsernameNotFoundException("Driver email not found");
        }
    }
}
