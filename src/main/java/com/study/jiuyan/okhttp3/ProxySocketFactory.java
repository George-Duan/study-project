package com.study.jiuyan.okhttp3;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProxySocketFactory extends SocketFactory {

    private final ProxyConfig proxyConfig;

    public ProxySocketFactory(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public Socket createSocket() {
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
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            socket.close();
            throw e;
        }
        return socket;
    }

    public Socket createSocket(InetAddress address, int port)
            throws IOException {
        Socket socket = createSocket();
        try {
            socket.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            socket.close();
            throw e;
        }
        return socket;
    }

    public Socket createSocket(String host, int port,
                               InetAddress clientAddress, int clientPort)
            throws IOException {
        Socket socket = createSocket();
        try {
            socket.bind(new InetSocketAddress(clientAddress, clientPort));
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            socket.close();
            throw e;
        }
        return socket;
    }

    public Socket createSocket(InetAddress address, int port,
                               InetAddress clientAddress, int clientPort)
            throws IOException {
        Socket socket = createSocket();
        try {
            socket.bind(new InetSocketAddress(clientAddress, clientPort));
            socket.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            socket.close();
            throw e;
        }
        return socket;
    }
}