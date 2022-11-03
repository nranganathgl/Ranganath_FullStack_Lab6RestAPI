package com.greatLearning.studentManagement.util;

import com.greatLearning.studentManagement.entity.Role;
import com.greatLearning.studentManagement.entity.User;
import com.greatLearning.studentManagement.repository.RoleRepository;
import com.greatLearning.studentManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Transactional
public class BootstrapAppData {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void insertEmployees(ApplicationReadyEvent event) {

        User vinay = new User();
        vinay.setUsername("vinay");
        vinay.setPassword(passwordEncoder.encode("welcome"));
        //vinay.setPassword("welcome");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        vinay.addRoles(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        vinay.addRoles(userRole);

        this.userRepository.save(vinay);

        User kiran = new User();
        kiran.setUsername("kiran");
        kiran.setPassword(passwordEncoder.encode("welcome"));
        //kiran.setPassword("welcome");

        //Role userRole = new Role();
        //userRole.setName("USER");
        Optional<Role> role = roleRepository.findByName("USER");

        if (role.isPresent()) {
            kiran.addRoles(role.get());
        }
        this.userRepository.save(kiran);
    }
}
