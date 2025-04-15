package com.team6.chat_service.auth.infrastructure.security;

import com.team6.chat_service.user.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String nickname;

    public CustomUserDetails(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return nickname;
    }
}
