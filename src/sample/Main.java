package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application implements Serializable
{

    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static ExtendedCell cell;                //UI Cell.
    public static ArrayList<String> namesOfStylesheets;

    public static final long serialVersionUID=122836328L;

    public static Queue<ExtendedPlayer> allPlayers;
    private static boolean gameOver;
    private static Scene scene;
    private static int currentN;
    private static int currentX;
    private static int currentY;

    public static final String SERIALIZE_RESUME_BOOL="serializeResumeBool.ser";
    public static final String SERIALIZE_QUEUE_FILE_UNDO="serializeQueueUndo.ser";
    public static final String SERIALIZE_GRID_FILE_UNDO="serializeGridUndo.ser";
    public static final String SERIALIZE_QUEUE_FILE_RESUME = "serializeQueueResume.ser";
    public static final String SERIALIZE_GRID_FILE_RESUME = "serializeGridUndo.ser";


    public static final int TYPE_UNDO = 0;
    public static final int TYPE_RESUME = 1;



    public static boolean resumeGameBool;

    public static Scene menu;

    public static Stage MainStage;

    public static boolean alertShown;

    static
    {
        currentX=2;
        currentX=9;
        currentY=6;
        resumeGameBool=false;
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
        alertShown=false;
    }

    public static void setResumeGameBool(boolean resumeGameBool)
    {
        Main.resumeGameBool=resumeGameBool;
    }

    public static void serializeResume() throws IOException
    {
        ObjectOutputStream out=null;
        try
        {
            out=new ObjectOutputStream(new FileOutputStream(SERIALIZE_RESUME_BOOL));
            out.writeBoolean(resumeGameBool);
        }
        finally
        {
            out.close();
        }
    }

    public static void deserializeResume() throws IOException
    {
        ObjectInputStream in=null;
        try
        {
            in=new ObjectInputStream(new FileInputStream(SERIALIZE_RESUME_BOOL));
            resumeGameBool=in.readBoolean();
        }
        finally
        {
            in.close();
        }
    }

    public Main()
    {
        System.out.println("Main called");
        allPlayers = new LinkedList<ExtendedPlayer>();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    public static void serializeQueue(int type) throws IOException
    {
        ObjectOutputStream out=null;
        String filename = "";
        if (type == TYPE_UNDO)
        {
            filename = SERIALIZE_QUEUE_FILE_UNDO;
        }
        else
        {
            filename = SERIALIZE_QUEUE_FILE_RESUME;
        }
        try
        {
            out=new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(allPlayers);
        }
        finally
        {
            out.close();
        }
    }

    public static void serializeGrid(int type) throws IOException
    {
        ObjectOutputStream out=null;
        String filename = "";
        if (type == TYPE_UNDO)
        {
            filename = SERIALIZE_GRID_FILE_UNDO;
        }
        else
        {
            filename = SERIALIZE_GRID_FILE_RESUME;
        }
        try
        {
            out=new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(gridPane);
        }
        finally
        {
            out.close();
        }
    }

    public static Queue<ExtendedPlayer> deserializeQueue(int type) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in=null;
        Queue<ExtendedPlayer> ret=null;
        String filename = "";
        if (type == TYPE_UNDO)
        {
            filename = SERIALIZE_QUEUE_FILE_UNDO;
        }
        else
        {
            filename = SERIALIZE_QUEUE_FILE_RESUME;
        }
        try
        {
            in=new ObjectInputStream(new FileInputStream(filename));
            ret=(Queue<ExtendedPlayer>) in.readObject();
        }
        finally
        {
            in.close();
        }

        return ret;
    }


    public static ExtendedGrid deserializeGrid(int type) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in=null;
        ExtendedGrid ret=null;
        String filename = "";
        if (type == TYPE_UNDO)
        {
            filename = SERIALIZE_GRID_FILE_UNDO;
        }
        else
        {
            filename = SERIALIZE_GRID_FILE_RESUME;
        }
        try
        {
            in=new ObjectInputStream(new FileInputStream(filename));
            ret=(ExtendedGrid) in.readObject();
        }
        finally
        {
            in.close();
        }
        return ret;
    }


    public static Scene getGameScene(int numberOfPlayers, int x , int y) throws IOException
    {
        allPlayers.clear();
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

        Button undoButton=new Button("Undo");
        Button newGameButton=new Button("New Game");
        Button exitButton=new Button("Exit Game");

        undoButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            ExtendedGrid newGrid=null;
            Queue<ExtendedPlayer> newPlayers=null;
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Main.setResumeGameBool(true);
                try
                {
                    serializeResume();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    newGrid=deserializeGrid(TYPE_UNDO);
                    newPlayers=deserializeQueue(TYPE_UNDO);

                    System.out.println(allPlayers.toString()+" allplayers in undo");
                    System.out.println(newPlayers.toString()+" newplayers in undo");
                    Iterator<ExtendedPlayer> iterator=newPlayers.iterator();
                    while(iterator.hasNext())
                        System.out.println(iterator.next().getPlayerColourByString()+" player's turn");

                    System.out.println();
                    System.out.println("New Grid-deserialized");
                    newGrid.printGrid();
                    System.out.println();
                    System.out.println("gridPane");
                    gridPane.printGrid();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                //setGridPane(newGrid);
                compareGrid(newGrid);
                setPlayers(newPlayers);

                if(allPlayers.peek().hasTakenFirstMove())
                    setGridBorderColour(allPlayers.peek());
            }
        });

        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Main.setResumeGameBool(true);
                try
                {
                    serializeResume();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                startNewGame();
            }
        });


        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Main.setResumeGameBool(true);
                try
                {
                    serializeResume();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    showMenu();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        BorderPane rootY=new BorderPane();
        rootY.setRight(undoButton);
        rootY.setLeft(newGameButton);
        rootY.setCenter(exitButton);

        root1.getChildren().add(rootY);

        scene = new Scene(rootX, (x * 60) + 100, (y * 60) + 100, Color.AZURE);
        scene.getStylesheets().add(namesOfStylesheets.get(allPlayers.peek().getPlayerColour()));

        Main.serializeGrid(TYPE_UNDO);
        Main.serializeQueue(TYPE_UNDO);
        return scene;
    }

    public static void setPlayers(Queue<ExtendedPlayer> newPlayers)
    {
        int i=0;
        allPlayers=newPlayers;
        for(Object o: newPlayers.toArray())
        {
            ((ExtendedPlayer)allPlayers.toArray()[i]).setCurrentCells(((ExtendedPlayer) o).getCurrentCells());
            i++;
        }
    }


//    public static void setGridForResume()
//    {
//        Main.setGridBorderColour(allPlayers.peek());
//        for(int i=0;i<gridPane.getExtendedCells().size();i++)
//        {
//            gridPane.getExtendedCells().get(i).setCell(new StackPane());
//            gridPane.getExtendedCells().get(i).setGroup(new Group());
//            for(int j=0;j<gridPane.getExtendedCells().get(i).getNumberOfBallsPresent();j++)
//            {
//                Sphere newSphere=new Sphere(10);
//
//                gridPane.getExtendedCells().get(i).getGroup().getChildren().add()
//            }
//        }
//    }

    @SuppressWarnings("Duplicates")
    public static void compareGrid(ExtendedGrid newGrid)
    {
        for(int i=0;i<newGrid.getExtendedCells().size();i++)
        {
            if(newGrid.getExtendedCells().get(i).getPlayerOccupiedBy()!=null && newGrid.getExtendedCells().get(i).getPlayerOccupiedBy()!=gridPane.getExtendedCells().get(i).getPlayerOccupiedBy())
            {
                gridPane.getExtendedCells().get(i).setPlayer(newGrid.getExtendedCells().get(i).getPlayerOccupiedBy());
            }

            if(newGrid.getExtendedCells().get(i).getNumberOfBallsPresent()>gridPane.getExtendedCells().get(i).getNumberOfBallsPresent())
            {
                for(int j=0;j<newGrid.getExtendedCells().get(i).getNumberOfBallsPresent()-gridPane.getExtendedCells().get(i).getNumberOfBallsPresent();j++)
                {
                    Sphere sphere=ExtendedCell.staticGetSphere(Main.getColor(gridPane.getExtendedCells().get(i).getPlayerOccupiedBy()));

                    switch (gridPane.getExtendedCells().get(i).getNumberOfBallsPresent())
                    {
                        case 0:
                            sphere.setTranslateX(0);
                            break;

                        case 1:
                            sphere.setTranslateX(10);
                            break;

                        case 2:
                            sphere.setTranslateX(5);
                            sphere.setTranslateY(10);
                            break;

                        default:
                            break;
                    }



                    gridPane.getExtendedCells().get(i).getGroup().getChildren().add(sphere);
                }

                gridPane.getExtendedCells().get(i).setNumberOfBallsPresent(newGrid.getExtendedCells().get(i).getNumberOfBallsPresent());
            }
            else if(newGrid.getExtendedCells().get(i).getNumberOfBallsPresent()<gridPane.getExtendedCells().get(i).getNumberOfBallsPresent())
            {
                for(int j=0;j<gridPane.getExtendedCells().get(i).getNumberOfBallsPresent()-newGrid.getExtendedCells().get(i).getNumberOfBallsPresent();j++)
                {
                    gridPane.getExtendedCells().get(i).getGroup().getChildren().remove(j);
                }
                gridPane.getExtendedCells().get(i).setNumberOfBallsPresent(newGrid.getExtendedCells().get(i).getNumberOfBallsPresent());
            }

            gridPane.getExtendedCells().get(i).getCell().getChildren().clear();
            gridPane.getExtendedCells().get(i).getCell().getChildren().add(gridPane.getExtendedCells().get(i).getGroup());
        }

    }



    private static void startNewGame()
    {
        Main.launchGame(currentN, currentX, currentY);
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
        Scene newScene = null;
        try
        {
            newScene = getGameScene(n,x,y);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        currentN=n;
        currentX=x;
        currentY=y;
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
    private static void clickedOnCell(MouseEvent e, BooleanProperty cellSwitch, int x , int y ) throws IOException
    {
        if (!cellSwitch.get())
            cellSwitch.set(!cellSwitch.get());
        ExtendedPlayer curPlayer=null;

        serializeQueue(TYPE_UNDO);
        serializeGrid(TYPE_UNDO);

        Iterator<ExtendedPlayer> iterator=allPlayers.iterator();
        while(iterator.hasNext())
            System.out.println(iterator.next().getPlayerColourByString()+" player's turn");
        System.out.println();


        try
        {

            curPlayer = allPlayers.peek();
            ExtendedCell cellSelected = gridPane.getCellFromCoordinate(y, x);

//            makeSerializeData(1);
//            deserializeData(1);

            if (cellSelected.isCellOccupied())
            {
                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                if (curPlayer!=null && curCellColor == curPlayer.getPlayerColour())
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
//            e2.printStackTrace();
            showAlert(curPlayer);
            System.out.print("showAlert called from ");
            e2.printStackTrace();
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
            gridPane.printGrid();
            updatePlayerStats(allPlayers);  //remove dead players

            ExtendedPlayer nextPlayer = allPlayers.peek();
            setGridBorderColour(nextPlayer);
            if (curPlayer.isAlive()  && (!allPlayers.contains(curPlayer)))
            {
                allPlayers.add(curPlayer);
            }
            serializeQueue(TYPE_RESUME);
            serializeGrid(TYPE_RESUME);

            resumeGameBool = true;
            serializeResume();

            System.out.println(allPlayers.toString());
//            gridPane.printGrid();
//            makeSerializeData(1);
//            deserializeData(1);
            if (allPlayers.size() == 1)
            {
                gameOver=true;
                System.out.println("thuggs");
//                System.out.println("You might have won");
                showAlert(curPlayer);
                System.out.println("showAlert called from allplayerssize==1");
            }
        }
        catch (Exception e1)
        {
            System.out.println("thuggs");
//            e1.printStackTrace();
            showAlert(curPlayer);
            System.out.print("showAlert called from ");
            e1.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception
    {
        System.out.println("Stage is closing");
        super.stop();
    }

    public static void showAlert(ExtendedPlayer curPlayer)
    {
        if(!alertShown)
        {
            alertShown=true;
            resumeGameBool = false;
            try {
                serializeResume();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Alert gameoverDialog = new Alert(Alert.AlertType.NONE);
            gameoverDialog.setTitle("Game Over");
            gameoverDialog.setHeaderText(null);
            gameoverDialog.setContentText("Player " + curPlayer.getPlayerColourByString() + " won!");
            gameoverDialog.getButtonTypes().removeAll();
            ButtonType buttonType = new ButtonType("Return to Menu");
            gameoverDialog.getButtonTypes().add(buttonType);
            gameoverDialog.setOnHidden(evt ->
            {
                alertShown=false;
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
            try
            {
                clickedOnCell(e,cellSwitch,x,y);
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
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


    public static ExtendedGrid setGridPane(ExtendedGrid gridPane)
    {
        //System.out.println("came to grid pane");
        GridPane grid = new GridPane();
        for (int x = 0; x < gridPane.getSideLengthX(); x++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setPercentWidth(100);
//            cc.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0; y < gridPane.getSideLengthY(); y++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setPercentHeight(100);
//            rc.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rc);
        }

        BooleanProperty[][] switches=new BooleanProperty[gridPane.getSideLengthX()][gridPane.getSideLengthY()];

        for (int i = 0; i <gridPane.getExtendedCells().size() ; i++)
        {
            //System.out.println("for loop "+i);
            gridPane.getExtendedCells().get(i).setGroup(new Group());
            for(int j=0;j<gridPane.getExtendedCells().get(i).getNumberOfBallsPresent();j++)
            {
                gridPane.getExtendedCells().get(j).getGroup().getChildren().add(ExtendedCell.staticGetSphere(Main.getColor(gridPane.getExtendedCells().get(i).getPlayerOccupiedBy())));
            }
            //System.out.println(gridPane.getExtendedCells().get(i).getNumberOfBallsPresent());
            if(gridPane.getExtendedCells().get(i).getNumberOfBallsPresent()>0)
            {
                System.out.println(gridPane.getExtendedCells().get(i).getNumberOfBallsPresent() + " in " + gridPane.getExtendedCells().get(i).getCoordX() + " ," + gridPane.getExtendedCells().get(i).getCoordY() + " by player " + gridPane.getExtendedCells().get(i).getPlayerOccupiedBy().getPlayerColourByString());
                System.out.println(gridPane.getExtendedCells().get(i).getCriticalMass()+" cell is null"+gridPane.getExtendedCells().get(i).getGroup());


                gridPane.getExtendedCells().get(i).setCell(new StackPane());
                gridPane.getExtendedCells().get(i).getCell().getChildren().clear();
                gridPane.getExtendedCells().get(i).getCell().getChildren().add(gridPane.getExtendedCells().get(i).getGroup());

                gridPane.getExtendedCells().get(i).getCell().setOnMouseClicked(e ->
                {
                    try
                    {
                        clickedOnCell(e,new SimpleBooleanProperty(),gridPane.getSideLengthX(),gridPane.getSideLengthY());
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                });

                gridPane.getExtendedCells().get(i).getCell().getStyleClass().add("cell");
            }
        }



        //System.out.println("Came out of for loop");
        grid.getStyleClass().add("grid");
        gridPane.setGridPane(grid);
        Main.setGridBorderColour(allPlayers.peek());
        return gridPane;
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
