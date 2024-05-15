package com.uber.auth.services;

import com.uber.auth.dto.DriverDTO;
import com.uber.common.entities.Driver;
import com.uber.auth.repositories.DriverRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DriverRepository driverRepository;

    public AuthService(@Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Driver saveDriver(DriverDTO driverDTO) {
        log.info("Hello from controller...");
        Driver d = Driver.builder()
                .age(driverDTO.getAge())
                .name(driverDTO.getName())
                .email(driverDTO.getEmail())
                .password(this.bCryptPasswordEncoder.encode(driverDTO.getPassword()))
                .build();

        this.driverRepository.save(d);
        return d;
    }
}
