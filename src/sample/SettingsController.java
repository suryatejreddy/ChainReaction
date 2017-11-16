package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

import java.io.IOException;

public class SettingsController
{

    @FXML
    public ColorPicker playerCount0;

    @FXML
    public ColorPicker playerCount1;

    @FXML
    public ColorPicker playerCount2;

    @FXML
    public ColorPicker playerCount3;

    @FXML
    public ColorPicker playerCount4;

    @FXML
    public ColorPicker playerCount5;

    @FXML
    public ColorPicker playerCount6;

    @FXML
    public ColorPicker playerCount7;

    public static ArrayList<Color> selectedColors = new ArrayList<>();

    static {

    }


    public void setData(ComboBox playerCount)
    {

//        playerCount.getItems().clear();
//
//        playerCount.getItems().addAll(
//                "VIOLET",
//                "BLUE",
//                "GREEN",
//                "YELLOW",
//                "ORANGE",
//                "RED",
//                "BROWN",
//                "WHITE");
//
//        if(playerCount.equals(playerCount1))
//        {
//            playerCount.getSelectionModel().select(playerColour1);
//            System.out.println(playerCount1.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount2))
//        {
//            playerCount.getSelectionModel().select(playerColour2);
//            System.out.println(playerCount2.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount3))
//        {
//            playerCount.getSelectionModel().select(playerColour3);
//            System.out.println(playerCount3.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount4))
//        {
//            playerCount.getSelectionModel().select(playerColour4);
//            System.out.println(playerCount4.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount5))
//        {
//            playerCount.getSelectionModel().select(playerColour5);
//            System.out.println(playerCount5.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount6))
//        {
//            playerCount.getSelectionModel().select(playerColour6);
//            System.out.println(playerCount6.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount7))
//        {
//            playerCount.getSelectionModel().select(playerColour7);
//            System.out.println(playerCount7.getSelectionModel().getSelectedIndex());
//        }
//        else if(playerCount.equals(playerCount0))
//        {
//            playerCount.getSelectionModel().select(playerColour0);
//            System.out.println(playerCount0.getSelectionModel().getSelectedIndex());
//        }
    }

    public static void showWarning()
    {
        try
        {
            Main.playOnAlert();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Player colour clash");
        alert.setHeaderText(null);
        alert.setContentText("There is a clash in the colours of players. Please resolve this.");
        alert.show();
    }

    public static Color getSelectedColour(int i)
    {
        return selectedColors.get(i);
    }

    public void setData()
    {
        playerCount0.setValue(ExtendedPlayer.colorOfPlayer1);
        playerCount1.setValue(ExtendedPlayer.colorOfPlayer2);
        playerCount2.setValue(ExtendedPlayer.colorOfPlayer3);
        playerCount3.setValue(ExtendedPlayer.colorOfPlayer4);
        playerCount4.setValue(ExtendedPlayer.colorOfPlayer5);
        playerCount5.setValue(ExtendedPlayer.colorOfPlayer6);
        playerCount6.setValue(ExtendedPlayer.colorOfPlayer7);
        playerCount7.setValue(ExtendedPlayer.colorOfPlayer8);

        playerCount0.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer1 = playerCount0.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount1.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer2 = playerCount1.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount2.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer3 = playerCount2.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount3.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer4 = playerCount3.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount4.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer5 = playerCount4.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount5.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer6 = playerCount5.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount6.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer7 = playerCount6.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        playerCount7.setOnAction((ActionEvent t) ->{
            ExtendedPlayer.colorOfPlayer8 = playerCount7.getValue();
            addColorsToMainArray();
            Main.changeCSSforAllPlayers();
        });

        addColorsToMainArray();

    }


    public void initialize()
    {
        setData();
    }

    public void addColorsToMainArray()

    {
        selectedColors.clear();
        selectedColors.add(playerCount0.getValue());
        selectedColors.add(playerCount1.getValue());
        selectedColors.add(playerCount2.getValue());
        selectedColors.add(playerCount3.getValue());
        selectedColors.add(playerCount4.getValue());
        selectedColors.add(playerCount5.getValue());
        selectedColors.add(playerCount6.getValue());
        selectedColors.add(playerCount7.getValue());
        Main.selectedColors = new ArrayList<>(selectedColors);
    }


    public void backToGame()
    {
        try
        {
            Main.playOnRecurse();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(clashExists())
        {
            showWarning();
            return;
        }
        Main.selectedColors.clear();
        addColorsToMainArray();
        Main ob = new Main();
        ob.backToMenu();
    }

    public static Color[] getSelectedColors()
    {
        Color[] colourArray=new Color[8];
        for (int i=0;i<8;i++)
        {
            colourArray[i] = selectedColors.get(i);
        }
        return  colourArray;

    }

    public static boolean clashExists()
    {
        Color[] colourArray = getSelectedColors();
        int flag=0;

        for(int i=0;i<MenuController.numPlayers;i++)
        {
            Color color1 = colourArray[i];
            for(int j=i+1;j<MenuController.numPlayers;j++)
            {
                Color color2 = colourArray[j];
                if((color1.getRed() == color2.getRed()) && (color1.getGreen() == color2.getGreen()) && (color1.getBlue() == color2.getBlue()))
                {
                    flag=1;
                    break;
                }
            }
        }

        if(flag==0)
            return false;
        else
            return true;
    }
}
