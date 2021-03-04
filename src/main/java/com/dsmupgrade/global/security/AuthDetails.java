package com.dsmupgrade.global.security;

import com.dsmupgrade.domain.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class AuthDetails implements UserDetails {

    private final Student student;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        if (student.getIsAdmin()) {
            auth.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if (student.getIsFineManager()) {
            auth.add(new SimpleGrantedAuthority("FINE_MANAGER"));
        }
        if (student.getIsNoticeManager()) {
            auth.add(new SimpleGrantedAuthority("NOTICE_MANAGER"));
        }
        return auth;
    }

    @Override
    public String getUsername() {
        return student.getUsername();
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return student.getIsRegistered();
    }
}
