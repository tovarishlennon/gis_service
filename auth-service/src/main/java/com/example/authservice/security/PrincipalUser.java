package com.example.authservice.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonIgnoreProperties({"password"})
public class PrincipalUser extends User {
    private Long id;
    private String username;

    private List<String> usersRoles;

    public PrincipalUser(Long id, String username, String password, List<String> roles) {
        super(username, password, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.id = id;
        this.username = username;
    }
}
