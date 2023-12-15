package com.study.jiuyan.okhttp3;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LocalAddressSocketFactory extends SocketFactory {

    private final InetAddress localAddress;
    private final SocketFactory systemFactory = getDefault();

    public LocalAddressSocketFactory(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public Socket createSocket() throws IOException {
        Socket s = systemFactory.createSocket();
        s.bind(new InetSocketAddress(localAddress, 0));
        return s;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return systemFactory.createSocket(host, port, localAddress, 0);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        return systemFactory.createSocket(host, port, localAddr, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port) throws IOException {
        return systemFactory.createSocket(address, port, localAddress, 0);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        return systemFactory.createSocket(address, port, localAddr, localPort);
    }
}
