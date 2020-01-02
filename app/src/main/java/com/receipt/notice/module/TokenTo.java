package com.receipt.notice.module;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author youtui
 */
public class TokenTo implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NotNull
    @Override
    public String toString() {
        return "TokenTo{" +
                "token='" + token + '\'' +
                '}';
    }
}
