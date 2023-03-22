package com.santechture.api.service;

import com.santechture.api.secuirty.StoredToken;
import com.santechture.api.secuirty.authentication.MyUserDetails;
import com.santechture.api.secuirty.authentication.MyUserDetailsService;
import com.santechture.api.utils.JwtUtil;
import com.santechture.api.validation.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private StoredToken storedtoken;

    public String createAuthenticationToken(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        String jwt = jwtTokenUtil.generateToken(userDetails);
        storedtoken.putToken(request.getUsername().toLowerCase(), jwt);

        logger.info("{}, Logged in Successfully", userDetails.getUsername());
        return jwt;
    }

    public void logOut() {
        MyUserDetails user = (MyUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        storedtoken.removeTokenByKey(user.getUsername().toLowerCase());
        logger.info("{}, Logged out Successfully", user.getUsername());
    }
}