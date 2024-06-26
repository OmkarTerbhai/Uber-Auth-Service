package com.uber.auth.controllers;

import com.uber.auth.dto.SignUpDTO;
import com.uber.auth.dto.SignInDTO;
import com.uber.auth.services.AuthService;
import com.uber.auth.services.JWTService;
import com.uber.common.entities.Driver;
import com.uber.common.entities.Rider;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthService authService;

    private JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(@Autowired BCryptPasswordEncoder bCryptPasswordEncoder,
                          AuthenticationManager authenticationManager,
                          JWTService jwtService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @PostMapping("/signup/driver")
    public ResponseEntity<?> signupDriver(@RequestBody SignUpDTO signUpDTO) {

        Driver d = this.authService.saveDriver(signUpDTO);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @PostMapping("/signup/rider")
    public ResponseEntity<?> signupRider(@RequestBody SignUpDTO signUpDTO) {

        Rider d = this.authService.saveRiver(signUpDTO);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @PermitAll
    public ResponseEntity<?> signin(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));

        if(authentication.isAuthenticated()) {
            String jwtToken = this.jwtService.createJWTToken(null, signInDTO.getEmail());
            ResponseCookie responseCookie = ResponseCookie.from("JWT_Token", jwtToken)
                    .httpOnly(true)
                    .maxAge(3600)
                    .secure(false)
                    .path("/")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            return new ResponseEntity<>("Successfully Authenticated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authenticated", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request, HttpServletResponse response) {


        return new ResponseEntity<>("Undefeated, Undisputed", HttpStatus.OK);
    }
}
