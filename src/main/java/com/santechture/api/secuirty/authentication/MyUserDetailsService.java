package com.santechture.api.secuirty.authentication;

import com.santechture.api.entity.Admin;
import com.santechture.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsernameIgnoreCase(username);

        if(admin == null)
            throw new UsernameNotFoundException("Could not find user : ".concat(username));

        return MyUserDetails.build(admin);
    }
}