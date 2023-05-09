package com.example.authservice.model;

import jakarta.persistence.*;
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
public class Model extends BaseEntity implements Serializable {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users userId;

    private String name;
}
