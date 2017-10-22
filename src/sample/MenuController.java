package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    public ImageView myImage;

    @FXML
    public ComboBox playerCount;

    @FXML
    public ComboBox gridSize;

    @FXML
    public Button playGame;

    @FXML
    public Button resumeGame;

    @FXML
    public Button settings;


    public void setData(){

        playerCount.getItems().clear();

        playerCount.getItems().addAll(
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8");

        gridSize.getItems().clear();

        gridSize.getItems().addAll("9x6","15x10");
    }

    public void initialize() {
        setData();
    }







}

