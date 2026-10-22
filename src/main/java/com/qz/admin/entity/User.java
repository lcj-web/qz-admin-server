package com.qz.admin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qz_user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String role;
    private String email;
    private String lastLoginAt;

    public User(Long id, String username, String password, String nickname, String role, String email, String lastLoginAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
    }
}
