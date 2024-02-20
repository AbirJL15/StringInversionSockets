package socketstcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //PrintStream ps = new PrintStream(socket.getOutputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Envoi de la chaîne au serveur

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a message : ");
            String messageToServer = scanner.nextLine();
            writer.println(messageToServer);

            // Réception de la réponse  du serveur
            String reversedMessage = br.readLine();
            System.out.println("Reversed message : " + reversedMessage);


            // Fermeture de la connexion avec le serveur
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
