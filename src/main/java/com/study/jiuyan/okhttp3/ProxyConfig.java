package com.study.jiuyan.okhttp3;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.net.PasswordAuthentication;
import java.net.Proxy;

@Setter
@Getter
@Accessors(chain = true)
public class ProxyConfig {
    private Proxy proxy;
    private PasswordAuthentication authentication;
}
