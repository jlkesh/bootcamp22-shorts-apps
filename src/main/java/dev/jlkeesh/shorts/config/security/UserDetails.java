package dev.jlkeesh.shorts.config.security;

import dev.jlkeesh.shorts.entities.AuthUser;
import kotlin.text.UStringsKt;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final AuthUser authUser;
    private Long id;
    private String email;

    public UserDetails(@NonNull AuthUser authUser) {
        this.authUser = authUser;
        this.email = authUser.getEmail();
        this.id = authUser.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authUser.getRole()));
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
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
        return authUser.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
