package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;


class ServerTest {

    @Test
    void main() {
    }

    @Test
    void TestDownService() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        ServerSocket server = new ServerSocket(8080);
        Method method = Server.class.getDeclaredMethod("downService");
        method.setAccessible(true);
        method.invoke(server);
        Socket clientSocket = new Socket("localhost", 8080);
        clientSocket.close();
        out.close();

    }
}