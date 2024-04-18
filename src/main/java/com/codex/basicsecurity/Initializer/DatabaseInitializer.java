// package com.codex.basicsecurity.Initializer;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.codex.basicsecurity.model.UserEntity;
// import com.codex.basicsecurity.repository.UserEntityRepository;


// @Component
// public class DatabaseInitializer implements CommandLineRunner {

//     @Autowired
//     PasswordEncoder passwordEncoder;

//     @Autowired
//     UserEntityRepository userEntityRepository;



//     @Override
//     public void run(String... args) throws Exception {

//         UserEntity user = new UserEntity();
//         user.setUsername("admin");
//         user.setPassword(passwordEncoder.encode("admin"));
//         user.setName("ADMIN");
//         user.setEmail("admin@admin.com");
//         user.setMobile("8335898602");
//         user.setRole("ADMIN");
//         userEntityRepository.save(user);
//     }
// }

