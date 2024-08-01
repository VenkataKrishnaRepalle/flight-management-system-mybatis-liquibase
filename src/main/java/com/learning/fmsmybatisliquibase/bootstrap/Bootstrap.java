package com.learning.fmsmybatisliquibase.bootstrap;

import com.learning.fmsmybatisliquibase.dao.UserDao;
import com.learning.fmsmybatisliquibase.dao.UserRoleDao;
import com.learning.fmsmybatisliquibase.model.RoleType;
import com.learning.fmsmybatisliquibase.model.User;
import com.learning.fmsmybatisliquibase.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        if (userDao.count() < 2) {
            var user1 = User.builder()
                    .uuid(UUID.randomUUID())
                    .firstName("Venkata Krishna")
                    .lastName("Repalle")
                    .email("rvkrishna13052001@gmail.com")
                    .mobileNumber("9059712824")
                    .password(passwordEncoder.encode("venky123"))
                    .createdTime(Instant.now())
                    .updatedTime(Instant.now())
                    .build();
            userDao.insert(user1);

            userRoleDao.insert(UserRole.builder()
                    .userUuid(user1.getUuid())
                    .role(RoleType.CUSTOMER)
                    .build());

            var user2 = User.builder()
                    .uuid(UUID.randomUUID())
                    .firstName("Admin")
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .mobileNumber("9059712824")
                    .password(passwordEncoder.encode("admin"))
                    .createdTime(Instant.now())
                    .updatedTime(Instant.now())
                    .build();

            userDao.insert(user2);

            userRoleDao.insert(UserRole.builder()
                    .userUuid(user2.getUuid())
                    .role(RoleType.ADMIN)
                    .build());
        }
    }
}
