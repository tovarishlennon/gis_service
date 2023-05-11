package com.example.userservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
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
