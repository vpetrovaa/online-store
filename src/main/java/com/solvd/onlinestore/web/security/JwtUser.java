package com.solvd.onlinestore.web.security;

import com.solvd.onlinestore.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements Authentication {

    private boolean isAuthenticated;
    private String name;
    private String email;
    private User.Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role);
    }

    @Override
    public Object getCredentials() {
        return List.of(name, email, role.getAuthority());
    }

    @Override
    public Object getDetails() {
        return List.of(name, email, role.getAuthority());
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }

}
