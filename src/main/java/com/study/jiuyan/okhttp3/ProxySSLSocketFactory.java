package com.study.jiuyan.okhttp3;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;

/**
 * @author lile
 */
@Slf4j
public class ProxySSLSocketFactory extends SSLSocketFactory {
    private final ProxyConfig proxyConfig;
    private final SSLSocketFactory socketFactory;

    public static final X509TrustManager NOP_TRUST_MANAGER = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public static final HostnameVerifier NOP_HOSTNAME_VERIFIER = (s, sslSession) -> true;

    public static SSLContext NOP_TLSV12_SSL_CONTEXT;

    static {
        try {
            NOP_TLSV12_SSL_CONTEXT = SSLContext.getInstance("TLSv1.2");
            NOP_TLSV12_SSL_CONTEXT.init(null, new TrustManager[]{NOP_TRUST_MANAGER}, new java.security.SecureRandom());
        } catch (Exception e) {
            log.error("初始化SSLContext异常, error:", e);
        }
    }

    public ProxySSLSocketFactory(ProxyConfig proxyConfig, SSLSocketFactory socketFactory) {
        this.proxyConfig = proxyConfig;
        this.socketFactory = socketFactory;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return socketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return socketFactory.getSupportedCipherSuites();
    }

    public Socket createSocket()
            throws IOException {
        if (proxyConfig != null) {
            return new Socket(proxyConfig.getProxy());
        } else {
            return new Socket();
        }
    }

    public Socket createSocket(String host, int port)
            throws IOException {
        Socket socket = createSocket();
        try {
            return socketFactory.createSocket(socket, host, port, true);
        } catch (IOException e) {
            socket.close();
            throw e;
        }
    }

    public Socket createSocket(Socket s, String host,
                               int port, boolean autoClose)
            throws IOException {
        return socketFactory.createSocket(s, host, port, autoClose);
    }

    public Socket createSocket(InetAddress address, int port)
            throws IOException {
        Socket socket = createSocket();
        try {
            return socketFactory.createSocket(socket, address.getHostAddress(), port, true);
        } catch (IOException e) {
            socket.close();
            throw e;
        }
    }

    public Socket createSocket(String host, int port,
                               InetAddress clientAddress, int clientPort)
            throws IOException {
        Socket socket = createSocket();
        try {
            socket.bind(new InetSocketAddress(clientAddress, clientPort));
            return socketFactory.createSocket(socket, host, port, true);
        } catch (IOException e) {
            socket.close();
            throw e;
        }
    }

    public Socket createSocket(InetAddress address, int port,
                               InetAddress clientAddress, int clientPort)
            throws IOException {
        Socket socket = createSocket();
        try {
            socket.bind(new InetSocketAddress(clientAddress, clientPort));
            return socketFactory.createSocket(socket, address.getHostAddress(), port, true);
        } catch (IOException e) {
            socket.close();
            throw e;
        }
    }
}