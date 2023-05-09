package com.example.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Users extends BaseEntity implements Serializable {
    @Column(unique = true, name = "login")
    private String username;

    @Column(name = "password")
    private String password;
}
