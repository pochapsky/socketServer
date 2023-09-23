package org.example;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush();
            } catch (IOException ignored) {
            }
            try {
                while (true) {
                    word = in.readLine();
                    if (word.equals("stop")) {
                        this.downService();
                        break;
                    }
                    System.out.println(word);
                    CommonSettings.log("\n" + CommonSettings.currentTime + "Message sent from the socket was: " + word);
                    for (ClientHandler vr : Server.serverList) {
                        vr.send(word);
                    }
                }
            } catch (NullPointerException ignored) {
            }


        } catch (IOException e) {
            this.downService();
        }
    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ClientHandler vr : Server.serverList) {
                    if (vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}

public class Server {
    public static LinkedList<ClientHandler> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(CommonSettings.getPortFromFile(CommonSettings.settingsFileName));
        CommonSettings.log("\n" + CommonSettings.currentTime + " Server started");
        CommonSettings.log("\nServer awaiting connections...");
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                CommonSettings.log("\n" + CommonSettings.currentTime + "Connection from " + socket);
                try {
                    serverList.add(new ClientHandler(socket));


                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
