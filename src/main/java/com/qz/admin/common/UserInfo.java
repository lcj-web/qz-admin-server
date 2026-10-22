package com.qz.admin.common;

/**
 * 用户信息响应（不含密码等敏感字段）。
 */
public class UserInfo {

    private Long id;
    private String username;
    private String nickname;
    private String role;
    private String email;
    private String lastLoginAt;

    public UserInfo() {
    }

    public UserInfo(Long id, String username, String nickname, String role, String email, String lastLoginAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
