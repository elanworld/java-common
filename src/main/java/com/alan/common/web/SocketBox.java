package com.alan.common.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Socket检测
 *
 * @author wu xianNeng
 * @date 2021/12/31 11:02
 * @since JDK1.8
 */
public class SocketBox {
    public static boolean isOpen(int port) {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", port);
        try {
            socket.connect(socketAddress, 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getTouchableProxy() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("socks5://127.0.0.1:1080", 1080);
        map.put("socks5://127.0.0.1:7890", 7890);
        for (String key : map.keySet()) {
            if (isOpen(map.get(key))) {
                return key;
            }
        }
        return null;
    }
}
