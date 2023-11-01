package Lab2;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class Sender extends Thread {
    int port;
    String host;
    DatagramSocket socket;

    Sender(int port, String host) throws SocketException {
        this.port = port;
        this.host = host;
        this.socket = new DatagramSocket();
    }

    public void run() {
        // GUI components
        JFrame frame = new JFrame("Sender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea messageTextArea = new JTextArea(5, 30);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> {
            String message = messageTextArea.getText();
            byte[] buffer = message.getBytes(StandardCharsets.UTF_8);

            try {
                InetAddress address = InetAddress.getByName(host);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
                System.out.println("Message '" + message + "' has been sent");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Clear the text area
            messageTextArea.setText("");
        });

        JPanel panel = new JPanel();
        panel.add(messageTextArea);
        panel.add(sendButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws SocketException {
        Sender sender = new Sender(3001, "localhost");
        System.out.println("Chat starting...");
        sender.start();
    }
}
