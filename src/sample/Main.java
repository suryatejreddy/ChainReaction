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
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.print.attribute.standard.Media;

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

    public static void initColorForPlayers(Queue<ExtendedPlayer> tempPlayers)
    {
        for (ExtendedPlayer temp : tempPlayers)
        {
            temp.playerColor = Color.color(temp.r,temp.g,temp.b);
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
            initColorForPlayers(ret);
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

    public static void changeColorOfGrid(ExtendedPlayer curPlayer)
    {
        Color presentColor = curPlayer.getPlayerColour();
        String colorInString = toHex((int)(presentColor.getRed() * 255),(int) (presentColor.getGreen()*255), (int) (presentColor.getBlue()* 255));
        mainRoot.setStyle("cell-border-color: " + colorInString + " ;");
    }

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
        return player.getPlayerColour();
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

    public static boolean compareColors(Color color1 , Color color2)
    {
        if ((color1.getRed() == color2.getRed())  && (color1.getBlue() == color2.getBlue()) && (color1.getGreen() == color2.getGreen()))
        {
            return true;
        }
        return false;
    }

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

    @SuppressWarnings("Duplicates")
    private static void clickedOnCell(MouseEvent e, BooleanProperty cellSwitch, int x , int y ) throws IOException
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

    @Override
    public void stop() throws Exception
    {
        System.out.println("Stage is closing");
        super.stop();
    }

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

    public static String toHex(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }


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
        grid.getStyleClass().add("grid");
        gridPane.setGridPane(grid);
        changeColorOfGrid(allPlayers.peek());
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
