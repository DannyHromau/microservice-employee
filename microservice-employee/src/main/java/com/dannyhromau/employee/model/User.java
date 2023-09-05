package com.dannyhromau.employee.model;

import com.dannyhromau.employee.core.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usr")
public class User extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "blocked", nullable = false)
    private boolean blocked;
}
