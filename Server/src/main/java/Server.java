
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    // порт, который будет прослушивать наш сервер
    static final int PORT = 3443;
    // список клиентов, которые будут подключаться к серверу
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    public static Map<ClientHandler, Integer> players = new HashMap<ClientHandler, Integer>();
    public Server() {
        // сокет клиента, это некий поток, который будет подключаться к серверу
        // по адресу и порту
        Socket clientSocket = null;
        // серверный сокет
        ServerSocket serverSocket = null;
        try {
            // создаём серверный сокет на определенном порту
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            // запускаем бесконечный цикл
            while (true) {
                // таким образом ждём подключений от клиента
                clientSocket = serverSocket.accept();
                // создаём обработчик клиента, который подключился к серверу
                // this - это наш сервер
                ClientHandler client = new ClientHandler(clientSocket, this);
                System.out.println(client);
                clients.add(client);
                // каждое подключение клиента обрабатываем в новом потоке
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // отправляем сообщение всем клиентам
    public void sendMessageToAllClients(String msg) {
        for (Map.Entry<ClientHandler, Integer> pair : players.entrySet()) {
            pair.getKey().sendMsg(msg);
        }

    }

    public  void sehdMessageToOneClint (String msg, ClientHandler clientHandler){
        for (Map.Entry<ClientHandler, Integer> pair : players.entrySet()) {
            if (pair.getKey().equals(clientHandler)) {
                pair.getKey().sendMsg(msg);
            }
        }
    }

    public void addPlayers(ClientHandler clientHandler) {
        if (players.isEmpty()) players.put(clientHandler,1);
        else
        if (players.size() == 1) players.put(clientHandler,2);


    }

    // удаляем клиента из коллекции
    public void removeClient(ClientHandler client) {
        clients.remove(client);

    }
    public static void removePlayers(ClientHandler client) {
        players.remove(client);
    }

}