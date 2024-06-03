package com.uber.auth.filters;

import com.uber.auth.services.DriverDetailsService;
import com.uber.auth.services.JWTService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter("/api/vi/signin")
@Configuration
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private DriverDetailsService userDetailsService;

    private final RequestMatcher uriMatcher =
            new AntPathRequestMatcher("/api/vi/signin", HttpMethod.POST.name());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("In filter");
        Cookie cookie = null;
        String jwtToken = null;
        if(Objects.nonNull(request.getCookies())) {
            for (Cookie ck : request.getCookies()) {
                if ("JWT_Token".equals(ck.getName())) {
                    cookie = ck;
                    jwtToken = ck.getValue();
                }
            }
        }

        //Check if cookie is null
        if(Objects.isNull(cookie)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        else {
            System.out.println("Token in Filter : " + jwtToken);
            //Extract email from non-null JWT token
            String email = this.jwtService.getEmail(jwtToken);

            System.out.println("Email from token in filter : " + email);

            //Validate if user is from our system
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(jwtToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher matcher = new NegatedRequestMatcher(uriMatcher);
        return matcher.matches(request);
    }
}
