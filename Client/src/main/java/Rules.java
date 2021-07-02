import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class Rules {
    public static volatile RulesController rulesController;


    public Rules(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rules.fxml"));
            fxmlLoader.setController(rulesController);
            fxmlLoader.setRoot(rulesController);

            Parent root1 = fxmlLoader.load();
            rulesController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Rules");
            stage.setScene(new Scene(root1));
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.setOnCloseRequest(t -> {
                rulesController = null;
            });
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
