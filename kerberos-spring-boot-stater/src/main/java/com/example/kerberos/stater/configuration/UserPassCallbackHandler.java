package com.example.kerberos.stater.configuration;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class UserPassCallbackHandler implements CallbackHandler {

    private String user;
    private char[] pass;

    public UserPassCallbackHandler(String user, char[] pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    public void handle(Callback[] callbacks) {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nc = (NameCallback) callbacks[i];
                nc.setName(user);
            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback pc = (PasswordCallback) callbacks[i];
                pc.setPassword(pass);
            }
        }
    }

}
