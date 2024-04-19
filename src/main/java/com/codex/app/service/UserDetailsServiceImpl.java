package com.codex.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codex.app.model.MyUserDetails;
import com.codex.app.repository.UserEntityRepository;

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
