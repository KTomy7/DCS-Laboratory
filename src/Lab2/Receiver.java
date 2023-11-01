package Lab2;

import javax.swing.*;
import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.SocketException;

public class Receiver extends Thread {
    private final int port;
    private final JTextArea messageArea;

    public Receiver(int port, JTextArea messageArea) {
        this.port = port;
        this.messageArea = messageArea;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                int length = packet.getLength();
                String message = new String(packet.getData(), 0, length);

                SwingUtilities.invokeLater(() -> messageArea.append("Message is: " + message + "\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException {
        JFrame frame = new JFrame("Receiver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

        Receiver receiver = new Receiver(3001, messageArea);

        System.out.println("Chat starting ...");

        receiver.start();
    }
}
