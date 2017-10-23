package sample;

import NonUIComponents.Matrix;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static ExtendedCell cell;                //UI Cell.
    public static int clickedXPos;
    public static int clickedYPos;
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

    public Main() {
        allPlayers = new LinkedList<ExtendedPlayer>();
    }

//    public static void playGame(Scanner scanner, Matrix gameMatrix)
//    {
//        while(allPlayers.size() > 1)
//        {
//            Player curPlayer = allPlayers.peek();
//            System.out.println("Chance of player with " + curPlayer.getPlayerColourByString());
//            System.out.println("Enter coordinates");
//            int moveX = scanner.nextInt();
//            int moveY = scanner.nextInt();
//
//            Cell cellSelected = gameMatrix.getCellFromCoordinate(moveY,moveX);
//
//            if (cellSelected.isCellOccupied())
//            {
//                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
//                if (curCellColor == curPlayer.getPlayerColour())
//                { // check if player is adding to his color
//                    //add ball function
//                    cellSelected.addBall(curPlayer);
//                    allPlayers.remove(curPlayer);
//                }
//                else
//                {  //if not
//                    //show error, wrong move
//                    //we should not remove the player from the queue
//                    System.out.println("can't put ball here.already occupied by "+cellSelected.getPlayerOccupiedBy().getPlayerColourByString()+" player.");
//                    continue;
//                }
//            }
//            else
//            {
//                cellSelected.addBall(curPlayer);
//                allPlayers.remove(curPlayer);
//            }
//
//            for (Player randomPlayer : allPlayers)
//            {  //update status for all players to check if they are alive or dead
//                if (randomPlayer.hasTakenFirstMove())
//                {
//                    randomPlayer.checkPlayerStatus();
//                    if (!randomPlayer.isAlive())
//                    {
//                        allPlayers.remove(randomPlayer);
//                    }
//                }
//            }
//
//
//            if (curPlayer.isAlive())
//            {
//                allPlayers.add(curPlayer);
//            }
//
//            gameMatrix.printMatrix();
//        }
//
//        System.out.println(allPlayers.peek() + " Won! Yipeee");
//    }

//    public static void executeFirst()
//    {
//        int numberOfPlayers;
//        System.out.println("Enter number of players.");
//        Scanner scanner = new Scanner(System.in);
//        numberOfPlayers = scanner.nextInt();
//        int i = 0 ;
//
//        while(i<numberOfPlayers)
//        {
//            Player player = new Player(i, true);
//            allPlayers.add(player); //adding the player to the game
//            i++;
//        }
//        System.out.println("Enter X and Y.");
//        int x,y;
//        x=scanner.nextInt();
//        y=scanner.nextInt();
//        Matrix gameMatrix=new Matrix(x, y);
//    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene getGameScene(int numberOfPlayers, int x , int y)
    {
        int i = 0;

        while (i < numberOfPlayers)
        {
            ExtendedPlayer player = new ExtendedPlayer(i, true);
            allPlayers.add(player); //adding the player to the game
            i++;
        }
        System.out.println("Enter X and Y.");
//        Matrix gameMatrix=new Matrix(x, y);
//        setGameMatrix(gameMatrix);

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
        scene = new Scene(root, (x * 40) + 100, (y * 40) + 100, Color.AZURE);
        scene.getStylesheets().add(namesOfStylesheets.get(0));

//        while(allPlayers.size() > 1)
//        {
//            Player curPlayer = allPlayers.peek();
//            System.out.println("Chance of player with " + curPlayer.getPlayerColourByString());
//            //System.out.println("Enter coordinates");
//            //int moveX = scanner.nextInt();
//            //int moveY = scanner.nextInt();
//
//            Cell cellSelected = gameMatrix.getCellFromCoordinate(clickedYPos,clickedXPos);
//
//            if (cellSelected.isCellOccupied())
//            {
//                int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
//                if (curCellColor == curPlayer.getPlayerColour())
//                { // check if player is adding to his color
//                    //add ball function
//                    cellSelected.addBall(curPlayer);
//                    allPlayers.remove(curPlayer);
//                }
//                else
//                {  //if not
//                    //show error, wrong move
//                    //we should not remove the player from the queue
//                    System.out.println("can't put ball here.already occupied by "+cellSelected.getPlayerOccupiedBy().getPlayerColourByString()+" player."+clickedXPos+clickedYPos);
//                    continue;
//                }
//            }
//            else
//            {
//                cellSelected.addBall(curPlayer);
//                allPlayers.remove(curPlayer);
//            }
//
//            for (Player randomPlayer : allPlayers)
//            {  //update status for all players to check if they are alive or dead
//                if (randomPlayer.hasTakenFirstMove())
//                {
//                    randomPlayer.checkPlayerStatus();
//                    if (!randomPlayer.isAlive())
//                    {
//                        allPlayers.remove(randomPlayer);
//                    }
//                }
//            }
//
//
//            if (curPlayer.isAlive())
//            {
//                allPlayers.add(curPlayer);
//            }
//
//            gameMatrix.printMatrix();
//        }
//
//        System.out.println(allPlayers.peek() + " Won! Yipeee");


          return scene;
//        while(true)
//        {
//            if(gameOver)
//            {
//                System.out.println("Game Over... oops");
//                System.exit(0);
//            }
//        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

        Parent root = loader.load();

        Scene tempScene = new Scene(root,560,560);
        menu = tempScene;

//        MenuController myController = loader.getController();

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));

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

//    public static void setCellClicker(Player player, int x, int y)
//    {
//        cell=gridPane.getExtendedCells().get(0);
//        for(ExtendedCell c: gridPane.getExtendedCells())
//        {
//            if(c.getCoordX()==x && c.getCoordY()==y)
//            {
//                cell=c;
//                break;
//            }
//        }
//
//        Group group=new Group();
//        cell.getCell().setOnMouseClicked(new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent mouseEvent)
//            {
//                try
//                {
//                    System.out.println(x+" "+y);
//                    clickedXPos=x;
//                    clickedYPos=y;
//                    
//                    Player curPlayer=allPlayers.peek();
//                    Sphere sphere = new Sphere(10);
//                    PhongMaterial phongMaterial = new PhongMaterial();
//                    phongMaterial.setDiffuseColor(getColor(curPlayer));
//                    phongMaterial.setSpecularColor(Color.BLACK);
//                    sphere.setMaterial(phongMaterial);
//
//                    switch (group.getChildren().size())
//                    {
//                        case 0:
//                            sphere.setTranslateX(0);
//                            break;
//
//                        case 1:
//                            sphere.setTranslateX(10);
//                            break;
//
//                        case 2:
//                            sphere.setTranslateY(10);
//                            sphere.setTranslateX(5);
//                            break;
//
//                        default:
//                            break;
//                    }
//
//                    group.getChildren().add(sphere);
//                    if(cell.getCell().getChildren().size()==0)
//                        cell.getCell().getChildren().add(group);
//                    //cell.getCell().getChildren().forEach(l -> System.out.println(l.toString()));
//                }
//                catch(Exception e1)
//                {
//                    e1.printStackTrace();
//                }
//            }
//        });
//    }

    private static ExtendedCell createCell(BooleanProperty cellSwitch, int x, int y)
    {
        Group group = new Group();
        ExtendedCell cell = new ExtendedCell(group, new StackPane(), x, y, null, new ArrayList<ExtendedCell>());

        cell.getCell().setOnMouseClicked(e ->
        {
            if (!cellSwitch.get())
                cellSwitch.set(!cellSwitch.get());
            try
            {
                System.out.println("x and y: " + x + " " + y);
                for(int i=0;i<allPlayers.toArray().length;i++)
                {
                    System.out.println(allPlayers.toArray()[i].toString());
                }

                System.out.println();
                ExtendedPlayer curPlayer = allPlayers.peek();
                ExtendedPlayer nextPlayer= (ExtendedPlayer) allPlayers.toArray()[1];
                System.out.println("Current player who just took move : " + curPlayer);
                ExtendedCell cellSelected = gridPane.getCellFromCoordinate(y, x);
                int continueCondition=0;


                if (cellSelected.isCellOccupied())
                {
                    System.out.println("Came to cell is occupied.");
                    int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                    System.out.println("current cell colour: " + curCellColor);
                    if (curCellColor == curPlayer.getPlayerColour())
                    { // check if player is adding to his color
                        //add ball function
                        cellSelected.addBall(curPlayer);
                        allPlayers.remove(curPlayer);
                        setGridBorderColour(nextPlayer);
                    }
                    else
                    {  //if not
                        //show error, wrong move
                        //we should not remove the player from the queue
                        System.out.println("can't put ball here.already occupied by " + cellSelected.getPlayerOccupiedBy().getPlayerColourByString() + " player." + cellSelected.getCoordX() + cellSelected.getCoordY());
                        continueCondition=1;
                    }
                }
                else
                {
                    cellSelected.addBall(curPlayer);
                    allPlayers.remove(curPlayer);
                    setGridBorderColour(nextPlayer);
                }

                for (ExtendedPlayer randomPlayer : allPlayers)
                {  //update status for all players to check if they are alive or dead
                    if (randomPlayer.hasTakenFirstMove())
                    {

                        randomPlayer.checkPlayerStatus();
                        if (!randomPlayer.isAlive())
                        {
                            allPlayers.remove(randomPlayer);
                        }
                    }
                }


                if (curPlayer.isAlive() && continueCondition==0)
                {
                    allPlayers.add(curPlayer);
                }

                //gameMatrix.printMatrix();


//                PhongMaterial phongMaterial = new PhongMaterial();
//                phongMaterial.setDiffuseColor(getColor(curPlayer));
//                phongMaterial.setSpecularColor(Color.BLACK);
//                sphere.setMaterial(phongMaterial);
//                switch (group.getChildren().size()) {
//                    case 0:
//                        sphere.setTranslateX(0);
//                        break;
//
//                    case 1:
//                        sphere.setTranslateX(10);
//                        break;
//
//                    case 2:
//                        sphere.setTranslateY(10);
//                        sphere.setTranslateX(5);
//                        break;
//
//                    default:
//                        break;
//                }
//                group.getChildren().add(sphere);
//                if(cell.getCell().getChildren().size()==0)
//                    cell.getCell().getChildren().add(group);
                //cell.getCell().getChildren().forEach(l -> System.out.println(l.toString()));
            }
            catch (IndexOutOfBoundsException e1)
            {
                e1.printStackTrace();
                System.out.println(allPlayers.peek());
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }


            if (allPlayers.size() == 1)
            {
                System.out.println("Player" + allPlayers.peek() + " won.");
                gameOver=true;
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

    private static ExtendedGrid createGrid(int sidelengthX, int sidelengthY)
    {
        GridPane grid = new GridPane();
        ExtendedGrid returnGrid = new ExtendedGrid(new ArrayList<ExtendedCell>(), new GridPane(), sidelengthX, sidelengthY);
        ArrayList<ExtendedCell> returnExtendedCells = new ArrayList<ExtendedCell>();

        for (int x = 0; x < sidelengthX; x++)
        {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0; y < sidelengthY; y++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
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
