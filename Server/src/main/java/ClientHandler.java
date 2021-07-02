
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

// реализуем интерфейс Runnable, который позволяет работать с потоками
public class ClientHandler implements Runnable {
    // экземпляр нашего сервера
    private Server server;
    // исходящее сообщение
    private PrintWriter outMessage;
    // входящее собщение
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 3443;
    // клиентский сокет
    private Socket clientSocket = null;
    // количество клиента в чате, статичное поле
    private static int clients_count = 0;

    // конструктор, который принимает клиентский сокет и сервер
    public ClientHandler(Socket socket, Server server) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Переопределяем метод run(), который вызывается когда
    // мы вызываем new Thread(client).start();

    public void run() {
        try {
            while (true) {
                // сервер отправляет сообщение
                //               server.sendMessageToAllClients("Новый участник вошёл в чат!");
                //              server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
                System.out.println("new " + clients_count);
                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    // если клиент отправляет данное сообщение, то цикл прерывается и
                    // клиент выходит из чата
                    String[] msgstr = clientMessage.split("##");
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                    if (clientMessage.equalsIgnoreCase("##learn##1##")) {
                        sendMsg("##learn##0011200000112000001200012001120000011200000120001200112000001120000012000120011200000112000001200012##");
                    }

                    if (clientMessage.equalsIgnoreCase("##game##end##")) {
                        server.removePlayers(this);
                        Grid.emptyArray();
                    }
                    if (clientMessage.equalsIgnoreCase("##game##1##")) {
                        if (Server.players.size() < 2 ){
                            server.addPlayers(this);
                            server.sendMessageToAllClients("##game##"+ Grid.getGrid() + "##" + Server.players.get(this) +"##" );
                        }else{
                            sendMsg("##game##errorconnection##");

                        }
                    }
                    if (msgstr[1].equals("position")) {

                        server.sendMessageToAllClients("##game##"+Grid.addPosition(msgstr[2],msgstr[3],this) +"##");
                        Thread.sleep(1000);
                        server.sendMessageToAllClients("##game##"+Grid.deleteBols() +"##");
                        server.sendMessageToAllClients("##score##"+Grid.count_delete_white+":"+Grid.count_delete_black+"##");
                        if (Grid.collapsestep) {
                            server.sendMessageToAllClients("##collapsestep##");
                        }
                        else {
                            GameOver gameOver1 = new GameOver(Grid.grid, 1);
                            GameOver gameOver2 = new GameOver(Grid.grid, 2);
                            if (!gameOver1.gameOverCheck(Grid.grid, 1))
                                server.sendMessageToAllClients("##gameover##White##");
                            if (!gameOver2.gameOverCheck(Grid.grid, 2))
                                server.sendMessageToAllClients("##gameover33Black##");
                        }

                    }
                    // выводим в консоль сообщение (для теста)
                    System.out.println(clientMessage);
                    // отправляем данное сообщение всем клиентам
//                    server.sendMessageToAllClients(clientMessage);
                }
                // останавливаем выполнение потока на 100 мс
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
    }

    // отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
            //           System.out.println(msg);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // клиент выходит из чата
    public void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        //      server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
    }
}