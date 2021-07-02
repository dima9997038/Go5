import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class Learn {

    public static volatile LearnController learnControllerHandler;


    public Learn(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("learn.fxml"));
            fxmlLoader.setController(learnControllerHandler);
            fxmlLoader.setRoot(learnControllerHandler);

            Parent root1 = fxmlLoader.load();
            learnControllerHandler = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Go");
            stage.setScene(new Scene(root1));
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.setOnCloseRequest(t -> {
                try {
                    learnControllerHandler = null;
                    PrintWriter outMessage = new PrintWriter(MyControllerHandler.socket.getOutputStream());
                    outMessage.println("##game##end##");
                    outMessage.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }




}
