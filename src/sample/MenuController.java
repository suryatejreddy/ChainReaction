package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.Queue;


public class MenuController
{


    static int numPlayers;

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

    static
    {
        numPlayers=2;
    }

    public void setData()
    {

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

        gridSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                numPlayers=Integer.parseInt(playerCount.getValue().toString());
            }

        });
    }

    public void initialize() throws IOException
    {
        System.out.println("init called");
        setData();

        Main.deserializeResume();
        resumeGame.setDisable(!Main.resumeGameBool);

        resumeGame.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                ExtendedGrid newGrid=null;
                Queue<ExtendedPlayer> newPlayers=null;

                try
                {
                    newPlayers=Main.deserializeQueue();
                    newGrid=Main.deserializeGrid();
                    System.out.println(newPlayers.toString());
                    newGrid.printGrid();
                    Main.launchGame(newPlayers.size(), newGrid.getSideLengthX(), newGrid.getSideLengthY());
                    Main.allPlayers=Main.deserializeQueue();
                    Main.gridPane=Main.deserializeGrid();
                    Main.gridPane.printGrid();
                    Main.gridPane=Main.setGridPane(Main.gridPane);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startGame()
    {
        numPlayers = Integer.parseInt(playerCount.getValue().toString());
        int x;
        int y;

        if(SettingsController.clashExists())
        {
            SettingsController.showWarning();
            return;
        }

        String gridSizeData = (gridSize.getValue().toString());

        if (gridSizeData.compareTo("9x6") == 0)
        {
            x = 9;
            y = 6;
        }
        else
        {
            x = 15;
            y = 10;
        }

        System.out.println("starting now");
        Main.setResumeGameBool(true);
        try
        {
            Main.serializeResume();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        sample.Main.launchGame(numPlayers,x,y);
    }

    public void showSettings() throws Exception
    {
        Main ob = new Main();
        ob.showSettings();
    }
}

