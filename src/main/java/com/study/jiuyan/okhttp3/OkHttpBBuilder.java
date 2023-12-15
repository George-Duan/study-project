package com.study.jiuyan.okhttp3;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Setter
@Accessors(chain = true)
public class OkHttpBBuilder {
    private boolean enableCookies;
    private boolean sslTrust;
    private ProxyConfig proxyConfig;
    private InetAddress localInetAddress;
    private HttpLoggingInterceptor logInterceptor;
    private int timeout;

    public OkHttpClient.Builder build() {
        if(this.logInterceptor == null) {
            logInterceptor = new HttpLoggingInterceptor(log::info);//创建拦截对象
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//这一句一定要记得写，否则没有数据输出
        }

        HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addNetworkInterceptor(logInterceptor)  //设置打印拦截日志

                .retryOnConnectionFailure(false)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                ;

        if(timeout > 0) {
            //设置读取超时时间
            okHttpClientBuilder
                    // 读超时时间
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    // 写的超时时间
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    // 连接超时
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    // 整个流程潮湿时间
                    .callTimeout(timeout, TimeUnit.SECONDS)
            ;
        }

        if(enableCookies) {
            okHttpClientBuilder.cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    List<Cookie> oldCookies = cookieStore.get(url.host());
                    if(oldCookies == null) {
                        cookieStore.put(url.host(), cookies);
                    } else {
                        Map<String, Cookie> mergeCookie = new HashMap<>();
                        updateCookieMap(mergeCookie, oldCookies);
                        updateCookieMap(mergeCookie, cookies);
                        cookieStore.put(url.host(), new ArrayList<>(mergeCookie.values()));
                    }
                }

                private void updateCookieMap(Map<String, Cookie> cookieMap, List<Cookie> cookies) {
                    cookies.forEach(newCookie -> {
                        Cookie oldCookie = cookieMap.get(newCookie.name());
                        if(StringUtils.isNotBlank(newCookie.value()) && !"\"\"".equals(newCookie.value()) && !"-".equals(newCookie.value())) {
                            cookieMap.put(newCookie.name(), newCookie);
                        }
                    });
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<>();
                }
            });
        }

        // 设置出口IP
        if (localInetAddress != null) {
            okHttpClientBuilder.socketFactory(new LocalAddressSocketFactory(localInetAddress));
        }

        if(sslTrust) {
            // 如果未设置代理， 又需要信任所有证书
            okHttpClientBuilder
                    .sslSocketFactory(SSLSocketTrust.getSSLSocketFactory(), SSLSocketTrust.getX509TrustManager())
                    .hostnameVerifier(SSLSocketTrust.getHostnameVerifier());
        }

        if (proxyConfig != null) {
            switch (proxyConfig.getProxy().type()) {
                case SOCKS:
                    okHttpClientBuilder
                            .sslSocketFactory(new ProxySSLSocketFactory(proxyConfig, ProxySSLSocketFactory.NOP_TLSV12_SSL_CONTEXT.getSocketFactory()), ProxySSLSocketFactory.NOP_TRUST_MANAGER)
                            .socketFactory(new ProxySocketFactory(proxyConfig))
                            .hostnameVerifier(ProxySSLSocketFactory.NOP_HOSTNAME_VERIFIER)
                            .addInterceptor(chain -> {
                                boolean clearCredentials = false;
                                if (proxyConfig.getAuthentication() != null) {
                                    ThreadLocalProxyAuthenticator.getInstance().setCredentials(proxyConfig.getAuthentication());
                                    clearCredentials = true;
                                }
                                try {
                                    return chain.proceed(chain.request());
                                } finally {
                                    if (clearCredentials) {
                                        ThreadLocalProxyAuthenticator.getInstance().clearCredentials();
                                    }
                                }
                            });
                    break;
                case HTTP:
                    okHttpClientBuilder.proxy(proxyConfig.getProxy());
                    if (proxyConfig.getAuthentication() != null) {
                        okHttpClientBuilder.proxyAuthenticator((route, response) -> {
                            String credential = Credentials.basic(
                                    proxyConfig.getAuthentication().getUserName(),
                                    new String(proxyConfig.getAuthentication().getPassword()));
                            return response.request().newBuilder()
                                    .header("Proxy-Authorization", credential)
                                    .build();
                        });
                    }
                    break;
            }
        }

        return okHttpClientBuilder;
    }
}
