import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.PrintWriter;
import java.net.Socket;

public class MainApp extends Application {

    public static ClientWindow getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(ClientWindow currentClient) {
        MainApp.currentClient = currentClient;
    }

    private static volatile ClientWindow currentClient;



    public static void main(String[] args) throws Exception {
        ClientWindow clientWindow = new ClientWindow();
        currentClient=clientWindow;
        System.out.println("Main" + clientWindow.toString());
        setCurrentClient(clientWindow);
        launch(args);
    }

    public static volatile MyControllerHandler myControllerHandle;

    @Override
    public void start(Stage primaryStage) throws Exception {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
            loader.setController(myControllerHandle);
            loader.setRoot(myControllerHandle);
            Parent root = loader.load();
            myControllerHandle = loader.getController();
            System.out.println("Main " + myControllerHandle.toString());
            primaryStage.setTitle("Go Game");

            // выход из программы по закрытию окна
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent t) {
               stop();
                Platform.exit();
                System.exit(0);
            }
        });
            primaryStage.setScene(new Scene(root));

            primaryStage.show();

    }

    public void stop() {
        Socket socket = MainApp.getCurrentClient().getClientSocket();
        try {
            PrintWriter outMessage = new PrintWriter(socket.getOutputStream());
            outMessage.println("##session##end##");
            outMessage.flush();
            outMessage.close();
            System.out.println("I left");
            socket.close();
        } catch (Exception e) {
            e.getMessage();
        }


    }
}