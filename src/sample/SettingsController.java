package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class SettingsController {

    @FXML
    public ComboBox playerCount1;

    @FXML
    public ComboBox playerCount2;
    @FXML
    public ComboBox playerCount3;
    @FXML
    public ComboBox playerCount4;
    @FXML
    public ComboBox playerCount5;
    @FXML
    public ComboBox playerCount6;
    @FXML
    public ComboBox playerCount7;
    @FXML
    public ComboBox playerCount8;


    public void setData(ComboBox playerCount){

        playerCount.getItems().clear();

        playerCount.getItems().addAll(
                "VIOLET",
                "BLUE",
                "GREEN",
                "YELLOW",
                "ORANGE",
                "RED",
                "BROWN",
                "WHITE");

        playerCount.getSelectionModel().selectFirst();
    }

    public void initialize() {
        setData(playerCount1);
        setData(playerCount2);
        setData(playerCount3);
        setData(playerCount4);
        setData(playerCount5);
        setData(playerCount6);
        setData(playerCount7);
        setData(playerCount8);
    }

    public void backToGame(){
        Main ob = new Main();
        ob.backToMenu();
    }
}
