package socketstcp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int numport = 5000;
    private static final int maximum = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(maximum);

        try {
            ServerSocket serverSocket = new ServerSocket(numport);

            System.out.println("Waiting for connections .. ");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected:  " + clientSocket);

                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String messageFromClient = br.readLine();
                System.out.println("Client Message " + clientSocket + " : " + messageFromClient);

                // inversion de la chaîne
                String reversedMessage = new StringBuilder(messageFromClient).reverse().toString();
                writer.println(reversedMessage);

                // Bloquer le client en attente
                Thread.sleep(20000);  // 5000 millisecondes (5 secondes)

                System.out.println("Reversed Client message " + clientSocket);

                // Fermeture de la connexion
                clientSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
