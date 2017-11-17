package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.print.attribute.standard.Media;


/**
 * This is the core Class that runs the entire Game and consists of static methods and variables
 * that handle the gave environment. Functions for Initialization of the game, serialization, de serialization etc.
 * are present in this class.
 *
 * @author Suryatej and Vishaal
 * @version 1.1
 * @since 2017-10-26
 *
 */
public class Main extends Application implements Serializable
{

    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static ExtendedCell cell;                //UI Cell.
    public static ArrayList<String> namesOfStylesheets;

    public static final long serialVersionUID=122836328L;

    public static Queue<ExtendedPlayer> allPlayers;
    private static boolean gameOver;
    public static Scene scene;
    private static int currentN;
    private static int currentX;
    private static int currentY;

    public static final String SERIALIZE_RESUME_BOOL="serializeResumeBool.ser";
    public static final String SERIALIZE_QUEUE_FILE_UNDO="serializeQueueUndo.ser";
    public static final String SERIALIZE_GRID_FILE_UNDO="serializeGridUndo.ser";
    public static final String SERIALIZE_QUEUE_FILE_RESUME = "serializeQueueResume.ser";
    public static final String SERIALIZE_GRID_FILE_RESUME = "serializeGridResume.ser";


    public static final int TYPE_UNDO = 0;
    public static final int TYPE_RESUME = 1;

    public static ArrayList<Color> selectedColors;

    public static boolean resumeGameBool;

    public static Scene menu;

    public static Stage MainStage;

    public static boolean alertShown;

    public static StackPane mainRoot;

    public static boolean tempUsed = false;

    public static boolean isMultiplayer = false;

    public static Socket liveSocket;

    public static boolean isServer;

    static
    {
        currentX=2;
        currentX=9;
        currentY=6;
        resumeGameBool=false;
        gameOver=false;
        namesOfStylesheets=new ArrayList<String>();
        namesOfStylesheets.add("Stylesheets/grid-with-borders-1.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-2.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-3.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-4.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-5.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-6.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-7.css");
        namesOfStylesheets.add("Stylesheets/grid-with-borders-8.css");
        alertShown=false;
        selectedColors = new ArrayList<>();

    }

    /**
     * It is a setter function for the resumeGameBool that helps in serialization.
     * @param resumeGameBool
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-01
     */
    public static void setResumeGameBool(boolean resumeGameBool)
    {
        Main.resumeGameBool=resumeGameBool;
    }

    /**
     * We serialize the resumeBoolean that tells us if the previous game was completed or not.
     * @throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-01
     */

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

    /**
     * We deserialize the resumeBoolean that tells us if the previous game was completed or not.
     * @throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-01
     */

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

    /**
     * Function initializes a list of players.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-01
     */
    public Main()
    {
        allPlayers = new LinkedList<ExtendedPlayer>();
    }

    /**
     * It is a function that plays a sound upon click of a cell in the game.
     *@throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-6
     */
    public static void playOnClick() throws IOException
    {
        File onClick=new File("./src/AudioFiles/onClick.wav");
        javafx.scene.media.Media hit=new javafx.scene.media.Media(onClick.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(hit);
        Thread x=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.play();
            }
        });
        x.start();
        try
        {
            x.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * It is a function that plays a sound when user clicks on a button in the game.
     *@throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-6
     */
    public static void playOnRecurse() throws IOException
    {
        File onClick=new File("./src/AudioFiles/onButton.wav");
        javafx.scene.media.Media hit=new javafx.scene.media.Media(onClick.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(hit);
        Thread x=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.play();
            }
        });
        x.start();
        try
        {
            x.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * It is a function that plays a sound when an alert is raised for the user.
     *@throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-6
     */

    public static void playOnAlert() throws IOException
    {
        File onClick=new File("./src/AudioFiles/onAlert.wav");
        javafx.scene.media.Media hit=new javafx.scene.media.Media(onClick.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(hit);
        Thread x=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.play();
            }
        });
        x.start();
        try
        {
            x.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * It is a function that plays a sound when the game is completed.
     *@throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-6
     */

    public static void playOnEnd() throws IOException
    {
        File onClick=new File("./src/AudioFiles/gameOver.wav");
        javafx.scene.media.Media hit=new javafx.scene.media.Media(onClick.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(hit);
        Thread x=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.play();
            }
        });
        x.start();
        try
        {
            x.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * It is a function that plays a sound when the user tries to perform an invalid move.
     *@throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-6
     */
    public static void playOnError() throws IOException
    {
        File onClick=new File("./src/AudioFiles/error.wav");
        javafx.scene.media.Media hit=new javafx.scene.media.Media(onClick.toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(hit);
        Thread x=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.play();
            }
        });
        x.start();
        try
        {
            x.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Main function requires no introduction. It starts the javafx application.
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * It is a function that serializes the queue of players (i.e, storing current state of players in the game) into a file.
     * The file (undo or resume) depends on the input passed as a parameter.
     * @param type
     * @throws IOException
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
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

    /**
     * It is a function that serializes the entire grid ( all cells and children data ).
     * The file (undo or resume) depends on the input passed as a parameter.
     *
     * @param type
     * @throws IOException
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
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

    /**
     *It is a function that re constructs the JavaFx Color field from the rgb value as the Color field is not serializable.
     *
     * @param tempPlayers
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-5
     */
    public static void initColorForPlayers(Queue<ExtendedPlayer> tempPlayers)
    {
        for (ExtendedPlayer temp : tempPlayers)
        {
            temp.playerColor = Color.color(temp.r,temp.g,temp.b);
        }
    }

    /**
     * This functions deserializes the queue of players from the undo file or resume file depending on the type
     * passed as a parameter.
     *
     * @param type
     * @return Queue of Players
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */

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
            initColorForPlayers(ret);
        }
        finally
        {
            in.close();
        }

        return ret;
    }

    /**
     * This functions deserializes the game grid from the undo file or resume file depending on the type
     * passed as a parameter.
     *
     * @param type
     * @return Queue of Players
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */

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


    /**
     * This is a function that initializes the application and loads the starting menu.
     *
     * @param
     * @return Queue of Players
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-10-20
     */

    public static Scene getGameScene(int numberOfPlayers, int x , int y) throws IOException
    {
        allPlayers.clear();
        int i = 0;
        while (i<numberOfPlayers)
        {
            ExtendedPlayer player = new ExtendedPlayer(ExtendedPlayer.returnColorOfPlayer(i), true,i+1);
            allPlayers.add(player); //adding the player to the game
            i++;
        }


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
        mainRoot = root;

        AnchorPane root1 = new AnchorPane(root);
        root1.setPrefHeight(50);
        root1.setMaxHeight(50);
        root1.setMinHeight(50);
        BorderPane rootX=new BorderPane();
        rootX.setTop(root1);
        rootX.setCenter(root);

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
                try
                {
                    Main.playOnRecurse();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
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

                    setPlayers(newPlayers);
                    compareGrid(newGrid,newPlayers);

                    serializeGrid(Main.TYPE_RESUME);
                    serializeQueue(Main.TYPE_RESUME);
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
            }
        });

        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                try
                {
                    Main.playOnRecurse();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
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
                try
                {
                    Main.playOnRecurse();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
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

        if (x + y == 15)
        {
            scene = new Scene(rootX, 560, 560, Color.AZURE);
        }
        else
        {
            scene = new Scene(rootX, 780, 780, Color.AZURE);
        }

        scene.getStylesheets().clear();
        scene.getStylesheets().add("Stylesheets/grid-with-borders-1.css");
        changeColorOfGrid(allPlayers.peek());

        Main.serializeGrid(TYPE_UNDO);
        Main.serializeQueue(TYPE_UNDO);
        return scene;
    }

    /**
     * A function that changes the color of the grid for the next player to take a turn.
     * @param curPlayer
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
    public static void changeColorOfGrid(ExtendedPlayer curPlayer)
    {
        Color presentColor = curPlayer.getPlayerColour();
        String colorInString = toHex((int)(presentColor.getRed() * 255),(int) (presentColor.getGreen()*255), (int) (presentColor.getBlue()* 255));
        mainRoot.setStyle("cell-border-color: " + colorInString + " ;");
    }

    /**
     * A function that takes a paramter, the deserialzed queue of players and initilizes a new Queue to match the game environment.
     * @param newPlayers
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
    public static void setPlayers(Queue<ExtendedPlayer> newPlayers)
    {
        ArrayList<ExtendedPlayer> tempList = new ArrayList<>(newPlayers);
        allPlayers.clear();
        int numberOfPlayers = newPlayers.size();
        int i = 0;
        while (i<numberOfPlayers)
        {
            ExtendedPlayer player = new ExtendedPlayer(tempList.get(i).getPlayerColour(), true, i +1);
            allPlayers.add(player); //adding the player to the game
            i++;
        }
        System.out.println("Original list is  " + newPlayers);
        System.out.println("after deSerializing " + allPlayers);
    }

    /**
     * A function that takes in data de-serialized as parameters and compares with current game state to perform undo / resume functionalities.
     * @param newGrid
     * @param newPlayers
     *
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
    public static void compareGrid(ExtendedGrid newGrid, Queue<ExtendedPlayer> newPlayers)
    {
        for (int i=newGrid.getSideLengthY()-1;i>-1;i--)
        {
            for (int j =0;j<newGrid.getSideLengthX();j++)
            {
                ExtendedCell newCell = newGrid.getCellFromCoordinate(i,j);
                ExtendedCell currentCell = gridPane.getCellFromCoordinate(i,j);

                currentCell.emptyCell();

                if (newCell.isCellOccupied())
                {
                    for (int k =0 ; k < newCell.getNumberOfBallsPresent();k ++)
                    {
                        currentCell.addBall(getPlayerOfColor(newCell.getPlayerOccupiedBy().makeNewColor(),allPlayers),true,true);
                    }
                }

            }
        }
        ArrayList<ExtendedPlayer> tempList = new ArrayList<>();
        for (ExtendedPlayer p : newPlayers)
        {
            tempList.add(getPlayerOfColor(p.getPlayerColour(),allPlayers));
        }
        int n = allPlayers.size();
        allPlayers.clear();
        for (int i=0;i < n; i ++)
        {
            allPlayers.add(tempList.get(i));
        }
        changeColorOfGrid(allPlayers.peek());
    }

    /**
     * It is a function that searched for a player of the given color in the queue passed as a parameter.
     * @param color
     * @param players
     * @return
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-2
     */
    public static ExtendedPlayer getPlayerOfColor(Color color, Queue<ExtendedPlayer> players)
    {
        for (ExtendedPlayer p : players)
        {
            if (compareColors(p.playerColor,color))
            {
                return p;
            }
        }
        return null;
    }


    /**
     * This function starts a new game.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-24
     */
    private static void startNewGame()
    {
        Main.launchGame(currentN, currentX, currentY);
    }

    /**
     * This is a function that is called when the programs launches. It loads and renders the fxml files for the menu.
     * @param primaryStage
     * @throws Exception
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-10-24
     */
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

    /**
     * This is the golden function that is called when the user clicks on Play/Resume Game. It sets up the cells , arranges players in a queue
     * to play.
     * @param n
     * @param x
     * @param y
     *
     * @author Vishaal and Suryate
     * @version 1.0
     * @since 2017-10-24
     */
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
        if (x + y == 15)
        {
            MainStage.setWidth(560);
            MainStage.setMaxWidth(560);
            MainStage.setMinWidth(560);
            MainStage.setHeight(560);
            MainStage.setMaxHeight(560);
            MainStage.setMinHeight(560);
        }
        else
        {
            MainStage.setWidth(780);
            MainStage.setMaxWidth(780);
            MainStage.setMinWidth(780);
            MainStage.setHeight(780);
            MainStage.setMaxHeight(780);
            MainStage.setMinHeight(780);
        }
        MainStage.setScene(newScene);
    }

    /**
     * A function that loads and renders the Settings fxml to disaplay the settings page.
     * @throws Exception
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-24
     */

    public void showSettings() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML_Files/Settings.fxml"));

        Parent root = loader.load();

        MainStage.setScene( new Scene(root,560,560));

    }

    /**
     * A function that takes leaves the game and moves back to the meny screen.
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-24
     */
    public void backToMenu()
    {
        MainStage.setScene(menu);
    }


    /**
     * Return color of the player passed as a parameter.
     * @param player
     * @return
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-4
     */
    public static Color getColor(ExtendedPlayer player)
    {
        return player.getPlayerColour();
    }


    /**
     * This a function that is called after move to remove players who have died in that move. It uses the Iterator design principle .
     *
     * @param allPlayers
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-3
     */
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

    /**
     * A function that compares two colors based on rgb values.
     * @param color1
     * @param color2
     * @return true or false
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */
    public static boolean compareColors(Color color1 , Color color2)
    {
        if ((color1.getRed() == color2.getRed())  && (color1.getBlue() == color2.getBlue()) && (color1.getGreen() == color2.getGreen()))
        {
            return true;
        }
        return false;
    }

    /**
     * A function that enables/disables all the cells and renders them clickable/ un clickable accordingly.
     * @param flag
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */
    public static void setForAllCells(Boolean flag)
    {

        for (int i=gridPane.getSideLengthY()-1;i>-1;i--)
        {
            for (int j =0;j<gridPane.getSideLengthX();j++)
            {
                ExtendedCell tempCell = gridPane.getCellFromCoordinate(i,j);
                tempCell.getCell().setDisable(flag);
            }
        }
    }

    /**
     * Most important function. This function is called when a player clicks on a particularCell and is responsible for balls being added to the cell.
     * @param
     * @param cellSwitch
     * @param x
     * @param y
     * @throws IOException
     *
     * @author Vishaal and Suryatej
     * @version 1.0
     * @since 2017-10-24
     */
    @SuppressWarnings("Duplicates")
    public static void clickedOnCell(BooleanProperty cellSwitch, int x , int y ) throws IOException
    {

        if (!cellSwitch.get())
            cellSwitch.set(!cellSwitch.get());
        ExtendedPlayer curPlayer=null;


        Iterator<ExtendedPlayer> iterator=allPlayers.iterator();


        try
        {

            curPlayer = allPlayers.peek();
            ExtendedCell cellSelected = gridPane.getCellFromCoordinate(y, x);
            if (cellSelected.isCellOccupied())
            {
                Color curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour();
                if (curPlayer!=null && compareColors(curCellColor,curPlayer.getPlayerColour()))
                { // check if player is adding to his color
                    //add ball function
                    Main.playOnClick();
                    serializeQueue(TYPE_UNDO);
                    serializeGrid(TYPE_UNDO);
                    allPlayers.remove(curPlayer);
                    setForAllCells(true);
                    cellSelected.addBall(curPlayer,true,true);

                }
                else
                {  //if not
                    //show error, wrong move
                    //we should not remove the player from the queue
                    playOnError();
                }
            }
            else
            {
                Main.playOnClick();
                serializeQueue(TYPE_UNDO);
                serializeGrid(TYPE_UNDO);
                allPlayers.remove(curPlayer);
                setForAllCells(true);
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

    public static void multiplayerAddBall(int x , int y)
    {
        ExtendedCell cellClicked = gridPane.getCellFromCoordinate(y,x);
        ExtendedPlayer curPlayer;
        if (isServer)
        {
            curPlayer = getPlayerOfColor(Color.VIOLET,allPlayers);
        }
        else
        {
            curPlayer = getPlayerOfColor(Color.BLUE,allPlayers);
        }
        System.out.println("Callling addBall " + curPlayer + x + " "  + y);
        cellClicked.addBall(curPlayer,true,true);
    }

    public static void multiplayerChangeGridColor()
    {
        if (isServer)
        {
            changeColorOfGrid(getPlayerOfColor(Color.BLUE,allPlayers));
        }
        else
        {
            changeColorOfGrid(getPlayerOfColor(Color.VIOLET,allPlayers));
        }
    }




    public static void multiplayerReceivedCell(int x, int y)
    {
        multiplayerAddBall(x,y);
        multiplayerChangeGridColor();
    }

    public static void multiplayerClickedOnCell(int x , int y)
    {

        DataOutputStream temp = null;
        try {
            temp = new DataOutputStream(liveSocket.getOutputStream());
            temp.writeUTF(x + "," + y );
        } catch (IOException e) {
            e.printStackTrace();
        }
        multiplayerAddBall(x,y);
        multiplayerChangeGridColor();
    }



    /**
     * A function that loads and renders the menu fxml .
     * @throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-24
     */

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

    /**
     * A function that is called when all the recursive calls are completed and hence denotes the end of the move.
     * Players are removed here and game is prepared to take in next move.
     * @param curPlayer
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-1
     */
    public static void onAnimationCompleted(ExtendedPlayer curPlayer)
    {
        try{
//            gridPane.printGrid();
            updatePlayerStats(allPlayers);  //remove dead players
            ExtendedPlayer nextPlayer = allPlayers.peek();
            changeColorOfGrid(nextPlayer);
            setForAllCells(false);
            if (curPlayer.isAlive()  && (!allPlayers.contains(curPlayer)))
            {
                allPlayers.add(curPlayer);
            }

            serializeQueue(TYPE_RESUME);
            serializeGrid(TYPE_RESUME);

            resumeGameBool = true;
            serializeResume();

              if (allPlayers.size() == 1)
            {
                gameOver=true;
                System.out.println("thuggs");
                showAlert(curPlayer);
                System.out.println("showAlert called from allplayerssize==1");
            }
        }
        catch (Exception e1)
        {
            System.out.println("thuggs");
//            e1.printStackTrace();
            try
            {
                showAlert(curPlayer);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            System.out.print("showAlert called from ");
            e1.printStackTrace();
        }
    }

    /**
     * This function is called while quitting the mainStage.
     * @throws Exception
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-4
     */


    @Override
    public void stop() throws Exception
    {
        System.out.println("Stage is closing");
        super.stop();
    }

    /**
     * Function raises an alert once the game is completed.
     * @param curPlayer
     * @throws IOException
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-11-4
     */
    public static void showAlert(ExtendedPlayer curPlayer) throws IOException
    {
        playOnEnd();
        if(!alertShown)
        {
            alertShown=true;
            Alert gameoverDialog = new Alert(Alert.AlertType.NONE);
            gameoverDialog.setTitle("Game Over");
            gameoverDialog.setHeaderText(null);
            gameoverDialog.setContentText("Player " + curPlayer.playerNumber + " won!");
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
        resumeGameBool = false;
        try {
            serializeResume();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    /**
     * A function that creates a cell to be placed inside the grid and adds an event handler to it.
     * @param cellSwitch
     * @param x
     * @param y
     * @return Cell
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-24
     */
    private static ExtendedCell createCell(BooleanProperty cellSwitch, int x, int y)
    {
        Group group = new Group();
        ExtendedCell cell = new ExtendedCell(group, new StackPane(), x, y, null, new ArrayList<ExtendedCell>());

        cell.getCell().setOnMouseClicked(e ->
        {
            try
            {
                if (isMultiplayer)
                {
                    multiplayerClickedOnCell(x,y);
                }
                else{
                    clickedOnCell(cellSwitch,x,y);
                }
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
        });

        cell.getCell().getStyleClass().add("cell");
        return cell;
    }

    /**
     * A function that dynamically modifies the css files to change color of grid.
     *
     * @param color
     * @param fileName
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */

    public static void changeCss( Color color, String fileName)
    {
        List<String> lines = new ArrayList<String>();
        String line = null;
        try{
            File f1 = new File("src/" + fileName);
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.contains("java"))
                    line = line.replace("java", " ");
                lines.add(line);
            }
            fr.close();
            br.close();
            String colorInHex = toHex((int)(color.getRed()*255),(int)(color.getGreen()*255), (int)(color.getBlue()*255));
            String newLine = "    cell-border-color: " + colorInHex + " ;";
            lines.set(3, newLine);
            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            for(String s : lines)
                out.write(s + "\n");
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * A function that converts rgb to web based hex to use in the grid CSS.
     * @param r
     * @param g
     * @param b
     * @return String
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */
    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    /**
     * Helper function for above mentioned function.
     * @param number
     * @return
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */
    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }

    /**
     * Updates the CSS files to map to each player.
     *
     * @author Suryatej
     * @version 1.0
     * @since 2017-11-10
     */
    public static void changeCSSforAllPlayers()
    {
        changeCss(ExtendedPlayer.colorOfPlayer1,"Stylesheets/grid-with-borders-1.css");
        changeCss(ExtendedPlayer.colorOfPlayer2,"Stylesheets/grid-with-borders-2.css");
        changeCss(ExtendedPlayer.colorOfPlayer3,"Stylesheets/grid-with-borders-3.css");
        changeCss(ExtendedPlayer.colorOfPlayer4,"Stylesheets/grid-with-borders-4.css");
        changeCss(ExtendedPlayer.colorOfPlayer5,"Stylesheets/grid-with-borders-5.css");
        changeCss(ExtendedPlayer.colorOfPlayer6,"Stylesheets/grid-with-borders-6.css");
        changeCss(ExtendedPlayer.colorOfPlayer7,"Stylesheets/grid-with-borders-7.css");
        changeCss(ExtendedPlayer.colorOfPlayer8,"Stylesheets/grid-with-borders-8.css");

    }

    /**
     * A function that creates a game grid based on X and Y coordinates. It creates cells and sets basic css.
     * @param sidelengthX
     * @param sidelengthY
     * @return
     *
     * @author Vishaal
     * @version 1.0
     * @since 2017-10-23
     */
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
