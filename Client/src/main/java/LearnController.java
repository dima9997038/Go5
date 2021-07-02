import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.PrintWriter;

public class LearnController {


    @FXML
    public GridPane grid;
    public Label score;

    public static void fillScore(String fill){
        Learn.learnControllerHandler.score.setText(fill);
    }

    public static void fillFormLearn(String fill) {
        char[] chars = fill.toCharArray();

        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (chars[count] == '1') {
                    Image image = new Image("whitecell.png");
                    fillcell(image,i,j);
                }
                if (chars[count] == '2') {
                    Image image = new Image("blackcell.png");
                    fillcell(image,i,j);

                }
                if (chars[count] == '0') {
                    Image image = new Image("emptycell.png");
                    fillcell(image,i,j);

                }

                count++;
            }

        }


    }
    private static void fillcell(Image img, Integer i, Integer j){
        ImageView pic = new ImageView();
        pic.setFitWidth(35);
        pic.setFitHeight(35);
        pic.setImage(img);
        GridPane.setHalignment(pic, HPos.CENTER);
        GridPane.setValignment(pic, VPos.CENTER);
        Learn.learnControllerHandler.grid.add(pic, i, j);
    }

    public void clickGrid(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != grid) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse clicked cell col: " + colIndex + " row: " + rowIndex);
            try {

                    PrintWriter outMessage= new PrintWriter(MyControllerHandler.socket.getOutputStream());
                    outMessage.println("##position##"+ colIndex +"##"+ rowIndex +"##");
                    outMessage.flush();
                }
                catch (Exception exp){
                    exp.getMessage();
                }

        }
    }

}