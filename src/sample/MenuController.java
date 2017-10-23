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

        playerCount.getSelectionModel().selectFirst();
        gridSize.getItems().clear();

        gridSize.getItems().addAll("9x6","15x10");

        gridSize.getSelectionModel().selectFirst();
    }

    public void initialize() {
        setData();
    }

    public void startGame(){
        int numPlayers = Integer.parseInt(playerCount.getValue().toString());
        int x;
        int y;
        String gridSizeData = (gridSize.getValue().toString());

        if (gridSizeData.compareTo("9x6") == 0){
            x = 9;
            y = 6;
        }
        else{
            x = 15;
            y = 10;
        }

        System.out.println("starting now");
        sample.Main.launchGame(numPlayers,x,y);
    }

    public void showSettings() throws Exception
    {
        Main ob = new Main();
        ob.showSettings();
    }
}

