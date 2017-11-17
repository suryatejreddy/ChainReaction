package sample;


import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLSyntaxErrorException;
import java.util.Queue;

/**
 * Controller for Menu display
 *
 * @author Vishaal and Suraytej
 */

public class MenuController {


    public static final int PORT = 1234;
    public static final String SERVER = "localhost";

    static int numPlayers;
    static volatile int mainX;
    static volatile int mainY;

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

    static {
        numPlayers = 2;
    }


    /**
     * Sets initial values for the combo boxes and adds listeners for the combo box action events.
     *
     * @author Suryatej
     * @version 1.1
     * @since 2017-11-16
     */

    public void setData() {

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

        playerCount.setOnAction((Event t2) -> {
            numPlayers = Integer.parseInt(playerCount.getValue().toString());
        });
        gridSize.getItems().clear();

        gridSize.getItems().addAll("9x6", "15x10");

        gridSize.getSelectionModel().selectFirst();

        gridSize.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                numPlayers = Integer.parseInt(playerCount.getValue().toString());
            }

        });
    }


    /**
     * Initializer funtion to add action handler for Resume button.
     *
     * @throws IOException
     * @author Suraytej
     * @version 1.4
     * @since 2017-11-01
     */

    public void initialize() throws IOException {
        System.out.println("init called");
        setData();

        Main.deserializeResume();

        Main.changeCSSforAllPlayers();


        resumeGame.setDisable(!Main.resumeGameBool);

        resumeGame.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.playOnRecurse();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                ExtendedGrid newGrid = null;
                Queue<ExtendedPlayer> newPlayers = null;

                try {
                    newPlayers = Main.deserializeQueue(Main.TYPE_RESUME);
                    newGrid = Main.deserializeGrid(Main.TYPE_RESUME);


                    Main.launchGame(newPlayers.size(), newGrid.getSideLengthX(), newGrid.getSideLengthY());
                    Main.setPlayers(newPlayers);
                    Main.initColorForPlayers(Main.allPlayers);
                    Main.initColorForPlayers(newPlayers);
                    Main.compareGrid(newGrid, newPlayers);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Action Handler for Play Game Button.
     *
     * @author Suryatej
     * @version 1.2
     * @since 2017-11-26
     */

    public void startGame() {
        try {
            Main.playOnRecurse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        numPlayers = Integer.parseInt(playerCount.getValue().toString());
        int x;
        int y;

        if (Main.selectedColors.size() > 0) {
            if (SettingsController.clashExists()) {
                SettingsController.showWarning();
                return;
            }
        }

        String gridSizeData = (gridSize.getValue().toString());

        if (gridSizeData.compareTo("9x6") == 0) {
            x = 6;
            y = 9;
        } else {
            x = 10;
            y = 15;
        }

        System.out.println("starting now");
        Main.setResumeGameBool(true);
        try {
            Main.serializeResume();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sample.Main.launchGame(numPlayers, x, y);
    }


    /**
     * Action Handler for Settings Button. Opens the settings display page.
     *
     * @throws Exception
     * @author Vishaal
     * @version 1.1
     * @since 2017-11-14
     */

    public void showSettings() throws Exception {
        Main.playOnRecurse();
        Main ob = new Main();
        ob.showSettings();
    }

    /**
     * Action Handler for Start Online Game Button.
     *
     * @throws IOException
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-16
     */

    public void startServer() throws IOException {
        Main.playOnRecurse();
        ServerSocket me = new ServerSocket(PORT);

        while (true) {
            Socket connection = me.accept();
            System.out.println("Connected");
            ConnectionHandler handler=new ConnectionHandler(connection);
            handler.run();
            if (connection.isConnected())
                break;
        }
    }

    /**
     * Action Handler for Connect To Online Game Button
     *
     * @throws IOException
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-16
     */


    public void startClient() throws IOException {
        Main.isMultiplayer = true;
        Main.isServer = false;
        Main.playOnRecurse();
        Socket server = new Socket(SERVER, PORT);
        DataInputStream in = new DataInputStream(server.getInputStream());
        System.out.println(in.readUTF());
        Main.liveSocket = server;
        Main.launchGame(2, 9, 6);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String a = in.readUTF().toString();
                        int x = Integer.parseInt(a.substring(0, 1));
                        int y = Integer.parseInt(a.substring(2, 3));
//                        if (x == mainX && y == mainY)
//                        {
//                            System.out.println("Skipping");
//                            continue;
//                        }
                        mainX = x;
                        mainY = y;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Main.multiplayerReceivedCell(x,y);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public void onPlsWorkDone(DataInputStream in) {
        try {
            Main.clickedOnCell(new SimpleBooleanProperty(), mainX, mainY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

