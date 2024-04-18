package com.codex.basicsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codex.basicsecurity.model.MyUserDetails;
import com.codex.basicsecurity.repository.UserEntityRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username)
                .map(MyUserDetails::new) // Constructor reference to MyUserDetails
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Could not find user: " + username);
                });
    }

}
