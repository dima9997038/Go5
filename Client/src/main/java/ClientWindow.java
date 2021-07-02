import javafx.application.Platform;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow {
    // адрес сервера
    private static final String SERVER_HOST = "localhost";
    // порт
    private static final int SERVER_PORT = 3443;
    // клиентский сокет
    private Socket clientSocket;
    // входящее сообщение
    private Scanner inMessage;
    // исходящее сообщение
    private PrintWriter outMessage;
    // имя клиента
    private String clientName = "";
    // получаем имя клиента
    public String getClientName() {
        return this.clientName;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    // конструктор
    public ClientWindow() {
        try {
            // подключаемся к серверу
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                // бесконечный цикл
                while (true) {

                    // если есть входящее сообщение
                    if (inMessage.hasNext()) {
                        final String inMes = inMessage.nextLine();
                        String [] command = inMes.split("##");
                        if (command[1].equals("learn")){


                            Platform.runLater(() -> {
                                try {
                                    LearnController.fillFormLearn(command[2]);

                                } catch (Exception  ex) {
                                    ex.printStackTrace();
                                }
                            });


                        }

                        if (command[1].equals("game")){
                            Platform.runLater(() -> {
                                try {
                                    LearnController.fillFormLearn(command[2]);

                                } catch (Exception  ex) {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        if (command[1].equals("score")){
                            System.out.println(command[2]);
                            Platform.runLater(() -> {
                                try {
                                    LearnController.fillScore(command[2]);

                                } catch (Exception  ex) {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        if (command[1].equals("collapsestep")){
                            JOptionPane.showMessageDialog(null, "Suicide move");
                        }
                        if (command[1].equals("gameover")){
                            JOptionPane.showMessageDialog(null, "Game Over!"+command[2]+"win!");
                        }

                        System.out.println(inMes);
                    }
                }
            } catch (Exception e) {
                System.out.println("exception");
            }
            // });
        }).start();

    }


}