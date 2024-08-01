package com.learning.fmsmybatisliquibase.security;

import com.learning.fmsmybatisliquibase.dao.UserDao;
import com.learning.fmsmybatisliquibase.dao.UserRoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        var user = userDao.getById(UUID.fromString(uuid));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var userRoles = userRoleDao.getByUserUuid(user.getUuid());

        Set<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.getRole()))
                .collect(Collectors.toSet());

        return new User(user.getUuid().toString(), user.getPassword(), authorities);
    }
}
