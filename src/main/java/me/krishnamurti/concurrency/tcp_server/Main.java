package me.krishnamurti.concurrency.tcp_server;

public class Main {
    private static final int PORT = 8888;
    private static final int THREADS = 4;

    public static void main(String[] args) {


        Server server = new Server(PORT, THREADS);
        server.startServer();

    }
}
