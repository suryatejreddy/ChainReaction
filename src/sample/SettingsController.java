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


/**
 * Controller class for Settings.fxml
 *
 * @author Suryatej
 */

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


    /**
     *Method to show an alert dialog when a clash exists between colours.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-16
     */

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

    /**
     * Sets initial values for the colour pickers and adds listeners for the colour picker action events.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-16
     */

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

    /**
     * Calls initializer function setData()
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */

    public void initialize()
    {
        setData();
    }


    /**
     * Adds Colour Picker colours of the players to the selectedColors array.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-06
     */

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


    /**
     * Function to go back to the Menu display.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */

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


    /**
     * Sets an array to all the player colours.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-22
     * @return An array of player colours.
     */

    public static Color[] getSelectedColors()
    {
        Color[] colourArray=new Color[8];
        for (int i=0;i<8;i++)
        {
            colourArray[i] = selectedColors.get(i);
        }
        return  colourArray;

    }


    /**
     * Method to check if clash exists between the selected colours of the players.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-20
     * @return boolean to check if clash exists or not
     */

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
