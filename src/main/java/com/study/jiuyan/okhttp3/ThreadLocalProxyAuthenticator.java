package com.study.jiuyan.okhttp3;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * JDK全局的代理授权， HttpClient用ThreadLocalCredentialsProvider即可
 */
public class ThreadLocalProxyAuthenticator extends Authenticator {
    private final ThreadLocal<PasswordAuthentication> credentials = new ThreadLocal<>();

    {
        // 设置JDK全局的代理授权， 使用this
        Authenticator.setDefault(this);
    }

    private static class SingletonHolder {
        private static final ThreadLocalProxyAuthenticator instance = new ThreadLocalProxyAuthenticator();
    }
    public static ThreadLocalProxyAuthenticator getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 设置当前线程的代理授权
     * @param user 代理用户名
     * @param password 代理密码
     */
    public void setCredentials(String user, String password) {
        setCredentials(new PasswordAuthentication(user, password.toCharArray()));
    }
    /**
     * 设置当前线程的代理授权
     */
    public void setCredentials(PasswordAuthentication authentication) {
        credentials.set(authentication);
    }

    /**
     * 清除当前线程的代理授权
     */
    public void clearCredentials() {
        credentials.set(null);
    }

    /**
     * 获取代理授权
     * @return 用户名密码
     */
    public PasswordAuthentication getPasswordAuthentication() {
        return credentials.get();
    }
}