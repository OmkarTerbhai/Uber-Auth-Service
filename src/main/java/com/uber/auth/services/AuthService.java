package com.uber.auth.services;

import com.uber.auth.dto.SignUpDTO;
import com.uber.auth.repositories.RiderRepository;
import com.uber.common.entities.Driver;
import com.uber.auth.repositories.DriverRepository;
import com.uber.common.entities.Rider;
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

    @Autowired
    private RiderRepository riderRepository;

    public AuthService(@Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Driver saveDriver(SignUpDTO signUpDTO) {
        Driver d = Driver
                .builder()
                .name(signUpDTO.getName())
                .email(signUpDTO.getEmail())
                .licenseNumber("MH122344")
                .password(this.bCryptPasswordEncoder.encode(signUpDTO.getPassword()))
                .build();

        this.driverRepository.save(d);
        return d;
    }

    public Rider saveRiver(SignUpDTO signUpDTO) {
        Rider r = Rider
                .builder()
                .name(signUpDTO.getName())
                .email(signUpDTO.getEmail())
                .phoneNumber("9898989898")
                .password(this.bCryptPasswordEncoder.encode(signUpDTO.getPassword()))
                .build();

        this.riderRepository.save(r);
        return r;
    }
}
