package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application
{

    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static ExtendedCell cell;                //UI Cell.
    public static ArrayList<String> namesOfStylesheets;

    private static Queue<ExtendedPlayer> allPlayers;
    private static boolean gameOver;
    private static Scene scene;

    public static Scene menu;

    public static Stage MainStage;

    static
    {
        gameOver=false;
        namesOfStylesheets=new ArrayList<String>();
        namesOfStylesheets.add("Stylesheets/grid-with-borders-violet.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-blue.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-green.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-yellow.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-orange.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-red.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-brown.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-white.css");
    }

    public Main()
    {
        allPlayers = new LinkedList<ExtendedPlayer>();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    public static Scene getGameScene(int numberOfPlayers, int x , int y)
    {
        int i = 0;
        while (i<numberOfPlayers)
        {
            ExtendedPlayer player = new ExtendedPlayer(SettingsController.getSelectedColour(i), true);
            allPlayers.add(player); //adding the player to the game
            i++;
        }

        System.out.println("Enter X and Y.");

        BooleanProperty[][] switches = new BooleanProperty[x][y];
        for (i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                switches[i][j] = new SimpleBooleanProperty();
            }
        }

        gridPane = createGrid(x, y);
        StackPane root = new StackPane(gridPane.getGridPane());
        root.setStyle("-fx-background-color: null;");

        AnchorPane root1 = new AnchorPane(root);
        root1.setPrefHeight(50);
        root1.setMaxHeight(50);
        root1.setMinHeight(50);
        BorderPane rootX=new BorderPane();
        rootX.setTop(root1);
        rootX.setCenter(root);


//        ComboBox<String> comboBox=new ComboBox<String>();
//        comboBox.getItems().clear();
//        comboBox.getItems().addAll("New game", "Exit Game");
//        comboBox.getSelectionModel().selectFirst();
//        comboBox.setMaxWidth(150);
//        comboBox.setMinWidth(150);
//        comboBox.setPrefWidth(150);
//        root1.getChildren().add(comboBox);

        scene = new Scene(rootX, (x * 60) + 100, (y * 60) + 100, Color.AZURE);
        scene.getStylesheets().add(namesOfStylesheets.get(allPlayers.peek().getPlayerColour()));

        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML_Files/sample.fxml"));

        Parent root = loader.load();

        Scene tempScene = new Scene(root,560,560);
        menu = tempScene;

        MainStage = primaryStage;

        primaryStage.setScene(tempScene);
        primaryStage.show();
    }

    public static void launchGame(int n, int x , int y)
    {
        Scene newScene = getGameScene(n,x,y);
        MainStage.setScene(newScene);
    }

    public void showSettings() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML_Files/Settings.fxml"));

        Parent root = loader.load();

        MainStage.setScene( new Scene(root,560,560));

    }

    public void backToMenu()
    {
        MainStage.setScene(menu);
    }



    public static Color getColor(ExtendedPlayer player)
    {
        switch (player.getPlayerColour())
        {
            case 0:
                return Color.VIOLET;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.RED;
            case 6:
                return Color.BROWN;
            case 7:
                return Color.WHITE;
            default:
                return null;
        }
    }



    public static void updatePlayerStats(Queue<ExtendedPlayer> allPlayers)
    {
        for(Iterator<ExtendedPlayer> iter = allPlayers.iterator(); iter.hasNext(); ) {
            ExtendedPlayer randomPlayer = iter.next();
            if (randomPlayer.hasTakenFirstMove())
            {
                randomPlayer.checkPlayerStatus();
                if (!randomPlayer.isAlive())
                {
                    iter.remove();
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private static void clickedOnCell(MouseEvent e, BooleanProperty cellSwitch, int x , int y )
    {
        if (!cellSwitch.get())
            cellSwitch.set(!cellSwitch.get());
        ExtendedPlayer curPlayer=null;
        try
        {

            curPlayer = allPlayers.peek();
            ExtendedCell cellSelected = gridPane.getCellFromCoordinate(y, x);


            System.out.println("Before Making Move");
            makeSerializeData(1);
            deserializeData(1);

            if (cellSelected.isCellOccupied())
            {
                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                if (curCellColor == curPlayer.getPlayerColour())
                { // check if player is adding to his color
                    //add ball function
                    allPlayers.remove(curPlayer);
                    cellSelected.addBall(curPlayer,true,true);
                }
                else
                {  //if not
                    //show error, wrong move
                    //we should not remove the player from the queue
                }
            }
            else
            {
                allPlayers.remove(curPlayer);
                cellSelected.addBall(curPlayer,true,true);
            }



        }
        catch (IndexOutOfBoundsException e1)
        {
            e1.printStackTrace();
            System.out.println(allPlayers.peek());
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
            System.out.println("You might have won");
            System.out.println("Game over. yaay");
            Alert gameoverDialog= new Alert(Alert.AlertType.NONE);
            gameoverDialog.setTitle("Game Over");
            gameoverDialog.setHeaderText(null);
            gameoverDialog.setContentText("Player "+curPlayer.getPlayerColourByString()+" won!");
            gameoverDialog.getButtonTypes().removeAll();
            ButtonType buttonType=new ButtonType("Return to Menu");
            gameoverDialog.getButtonTypes().add(buttonType);
            gameoverDialog.setOnHidden(evt ->
            {
                try
                {
                    showMenu();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            });
            gameoverDialog.show();
        }


    }

    public static void showMenu() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../FXML_Files/sample.fxml"));
        Parent root = loader.load();

        Scene tempScene = new Scene(root,560,560);
        menu = tempScene;

        MainStage.setWidth(560);
        MainStage.setMinWidth(560);
        MainStage.setMaxWidth(560);
        MainStage.setHeight(560);
        MainStage.setMaxHeight(560);
        MainStage.setMinHeight(560);
        MainStage.setScene(tempScene);
        MainStage.show();
    }

    public static void onAnimationCompleted(ExtendedPlayer curPlayer)
    {
        try{
            updatePlayerStats(allPlayers);  //remove dead players
            ExtendedPlayer nextPlayer = allPlayers.peek();
            setGridBorderColour(nextPlayer);
            if (curPlayer.isAlive()  && (!allPlayers.contains(curPlayer)))
            {
                allPlayers.add(curPlayer);
            }
            System.out.println(allPlayers.toString());
//            gridPane.printGrid();
            System.out.println("After Making Move");
            makeSerializeData(1);
            deserializeData(1);
            if (allPlayers.size() == 1)
            {
                gameOver=true;
                System.out.println("thuggs");
                System.out.println("You might have won");
                System.out.println("Game over. yaay");
                Alert gameoverDialog= new Alert(Alert.AlertType.NONE);
                gameoverDialog.setTitle("Game Over");
                gameoverDialog.setHeaderText(null);
                gameoverDialog.setContentText("Player "+curPlayer.getPlayerColourByString()+" won!");
                gameoverDialog.getButtonTypes().removeAll();
                ButtonType buttonType=new ButtonType("Return to Menu");
                gameoverDialog.getButtonTypes().add(buttonType);
                gameoverDialog.setOnHidden(evt ->
                {
                    try
                    {
                        showMenu();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                });
                gameoverDialog.show();
            }
        }
        catch (Exception e1)
        {
            System.out.println("thuggs");
            e1.printStackTrace();
            System.out.println("You might have won");
            System.out.println("Game over. yaay");
            Alert gameoverDialog= new Alert(Alert.AlertType.NONE);
            gameoverDialog.setTitle("Game Over");
            gameoverDialog.setHeaderText(null);
            gameoverDialog.setContentText("Player "+curPlayer.getPlayerColourByString()+" won!");
            gameoverDialog.getButtonTypes().removeAll();
            ButtonType buttonType=new ButtonType("Return to Menu");
            gameoverDialog.getButtonTypes().add(buttonType);
            gameoverDialog.setOnHidden(evt ->
            {
                try
                {
                    showMenu();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            });
            gameoverDialog.show();
        }
    }


    public static void makeSerializeData(int file) throws IOException
    {
        ArrayList<SerializableCell> gameData = new ArrayList<SerializableCell>();
        for (ExtendedCell newCell : gridPane.getExtendedCells() )
        {
            SerializableCell serCell = new SerializableCell(newCell.getCoordX(),newCell.getCoordY(),-1,newCell.getNumberOfBallsPresent());
            if (newCell.isCellOccupied())
            {
                serCell.playerColor = newCell.getPlayerOccupiedBy().getPlayerColour();
            }
            gameData.add(serCell);
        }
        serializeData(gameData,file);

    }

    public static void serializeData(ArrayList<SerializableCell> gameData , int file) throws IOException
    {
        ObjectOutputStream out = null;
        String fileName = "data"+ Integer.toString(file) + ".ser";
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(gameData);
        }
        finally {
            out.close();
        }
    }


    public static void deserializeData(int file) throws IOException , ClassNotFoundException
    {
        ObjectInputStream in = null;
        ArrayList<SerializableCell> gameData = new ArrayList<SerializableCell>();
        String fileName = "data"+ Integer.toString(file) + ".ser";
        try
        {
            in = new ObjectInputStream(new FileInputStream(fileName));
            gameData = (ArrayList<SerializableCell>) in.readObject();
        }
        finally
        {
            in.close();
        }
        for (int i=gridPane.getSideLengthY()-1;i>-1;i--)
        {
            for (int j =0;j<gridPane.getSideLengthY();j++)
            {
                System.out.print(getSerializableCellFromCoordinate(gameData,i,j) + " \t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static SerializableCell getSerializableCellFromCoordinate(ArrayList<SerializableCell> gameData, int x, int y)
    {
        for (SerializableCell myCell : gameData)
        {
            if (myCell.x == x && myCell.y == y)
            {
                return myCell;
            }
        }
        return null;
    }



    private static ExtendedCell createCell(BooleanProperty cellSwitch, int x, int y)
    {
        Group group = new Group();
        ExtendedCell cell = new ExtendedCell(group, new StackPane(), x, y, null, new ArrayList<ExtendedCell>());

        cell.getCell().setOnMouseClicked(e ->
        {
            clickedOnCell(e,cellSwitch,x,y);
        });

        cell.getCell().getStyleClass().add("cell");
        return cell;
    }

    private static void setGridBorderColour(ExtendedPlayer curPlayer)
    {
        String colour=curPlayer.getPlayerColourByString().toLowerCase();
        namesOfStylesheets.forEach(e ->
        {
            if(e.contains(colour))
            {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(e);
            }
        });
    }

    private static ExtendedGrid createGrid(int sidelengthX, int sidelengthY)
    {
        GridPane grid = new GridPane();
        ExtendedGrid returnGrid = new ExtendedGrid(new ArrayList<ExtendedCell>(), new GridPane(), sidelengthX, sidelengthY);
        ArrayList<ExtendedCell> returnExtendedCells = new ArrayList<ExtendedCell>();

        for (int x = 0; x < sidelengthX; x++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setPercentWidth(100);
//            cc.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0; y < sidelengthY; y++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setPercentHeight(100);
//            rc.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rc);
        }

        BooleanProperty[][] switches=new BooleanProperty[sidelengthX][sidelengthY];
        for (int x = 0; x < sidelengthX; x++)
        {
            for (int y = 0; y < sidelengthY; y++)
            {
                switches[x][y]=new SimpleBooleanProperty();
                ExtendedCell cell = createCell(switches[x][y], x, sidelengthY-1-y);
                returnExtendedCells.add(cell);
                grid.add(cell.getCell(), x, y);
            }
        }

        grid.getStyleClass().add("grid");
        returnGrid.setGridPane(grid);
        returnGrid.setExtendedCells(returnExtendedCells);
        returnGrid.setCellDetails();
        returnGrid.initCells();
        return returnGrid;
    }

}
