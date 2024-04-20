package com.codex.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codex.app.modal.MyUserDetail;
import com.codex.app.repository.UserEntityRepository;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public MyUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username)
                .map(MyUserDetail::new) // Constructor reference to MyUserDetails
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Could not find user: " + username);
                });
    }

}
