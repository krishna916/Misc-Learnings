package me.krishnamurti.concurrency.tcp_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final int port;
    private final ExecutorService executorService;

    public Server(int port, int threads) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
           logger.info("Server listening on: " + this.port);
            while (true) {
                acceptConnection(serverSocket);
            }

        } catch (IOException e) {
            System.out.println("Server startup failed!!!");
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void acceptConnection(ServerSocket socket) throws IOException {
        var clientSocket = socket.accept();
        logger.info("New connection from: " + clientSocket.getInetAddress());

        executorService.submit(() ->processConnection(clientSocket));
    }

    private void processConnection(Socket socket) {
        logger.info("Started new request!!");

        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            int read = inputStream.read(bytes);

            Thread.sleep( TimeUnit.SECONDS.toMillis(10));

            OutputStream outputStream = socket.getOutputStream();
            String response = "HTTP/1.1 200 OK \r\n\r\nHello World!\r\n";
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

}
