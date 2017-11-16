package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class SettingsController
{

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
    public ComboBox playerCount7;   //this is the last combobox

    @FXML
    public ComboBox playerCount0;   //this is the first combobox

    public static int playerColour0;

    public static int playerColour1;

    public static int playerColour2;

    public static int playerColour3;

    public static int playerColour4;

    public static int playerColour5;

    public static int playerColour6;

    public static int playerColour7;

    static
    {
        playerColour0=0;
        playerColour1=1;
        playerColour2=2;
        playerColour3=3;
        playerColour4=4;
        playerColour5=5;
        playerColour6=6;
        playerColour7=7;
    }


    public void setData(ComboBox playerCount)
    {

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

        if(playerCount.equals(playerCount1))
        {
            playerCount.getSelectionModel().select(playerColour1);
            System.out.println(playerCount1.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount2))
        {
            playerCount.getSelectionModel().select(playerColour2);
            System.out.println(playerCount2.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount3))
        {
            playerCount.getSelectionModel().select(playerColour3);
            System.out.println(playerCount3.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount4))
        {
            playerCount.getSelectionModel().select(playerColour4);
            System.out.println(playerCount4.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount5))
        {
            playerCount.getSelectionModel().select(playerColour5);
            System.out.println(playerCount5.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount6))
        {
            playerCount.getSelectionModel().select(playerColour6);
            System.out.println(playerCount6.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount7))
        {
            playerCount.getSelectionModel().select(playerColour7);
            System.out.println(playerCount7.getSelectionModel().getSelectedIndex());
        }
        else if(playerCount.equals(playerCount0))
        {
            playerCount.getSelectionModel().select(playerColour0);
            System.out.println(playerCount0.getSelectionModel().getSelectedIndex());
        }
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

    public static int getSelectedColour(int i)
    {
        switch(i)
        {
            case 0: return playerColour0;
            case 1: return playerColour1;
            case 2: return playerColour2;
            case 3: return playerColour3;
            case 4: return playerColour4;
            case 5: return playerColour5;
            case 6: return playerColour6;
            case 7: return playerColour7;
            default: return -1;
        }
    }

    public void initialize()
    {
        setData(playerCount1);
        setData(playerCount2);
        setData(playerCount3);
        setData(playerCount4);
        setData(playerCount5);
        setData(playerCount6);
        setData(playerCount7);
        setData(playerCount0);

        playerCount0.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour0=(int)t1;
                System.out.println(playerColour0);
            }
        });

        playerCount1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour1=(int)t1;
                System.out.println(playerColour1);
            }
        });

        playerCount2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour2=(int)t1;
                System.out.println(playerColour2);
            }
        });

        playerCount3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour3=(int)t1;
                System.out.println(playerColour3);
            }
        });

        playerCount4.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour4=(int)t1;
                System.out.println(playerColour4);
            }
        });

        playerCount5.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour5=(int)t1;
                System.out.println(playerColour5);
            }
        });

        playerCount6.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour6=(int)t1;
                System.out.println(playerColour6);
            }
        });

        playerCount7.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                playerColour7=(int)t1;
                System.out.println(playerColour7);
            }
        });
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
        Main ob = new Main();
        ob.backToMenu();
    }

    public static boolean clashExists()
    {
        int[] colourArray=new int[8];
        colourArray[0]=playerColour0;
        colourArray[1]=playerColour1;
        colourArray[2]=playerColour2;
        colourArray[3]=playerColour3;
        colourArray[4]=playerColour4;
        colourArray[5]=playerColour5;
        colourArray[6]=playerColour6;
        colourArray[7]=playerColour7;

        int flag=0;

        for(int i=0;i<MenuController.numPlayers;i++)
        {
            for(int j=i+1;j<MenuController.numPlayers;j++)
            {
                if(colourArray[i]==colourArray[j])
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
