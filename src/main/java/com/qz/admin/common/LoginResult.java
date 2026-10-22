package com.qz.admin.common;

/**
 * 登录返回结果：token + 用户信息。
 */
public class LoginResult {

    private String token;
    private UserInfo user;

    public LoginResult() {
    }

    public LoginResult(String token, UserInfo user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
