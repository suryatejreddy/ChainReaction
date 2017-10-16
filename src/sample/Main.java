package sample;

import NonUIComponents.Cell;
import NonUIComponents.Matrix;
import NonUIComponents.Player;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main extends Application
{

    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static ExtendedCell cell;                //UI Cell.
    public static int clickedXPos;
    public static int clickedYPos;

    private static Queue<Player> allPlayers;
    private static Matrix gameMatrix;

    public Main()
    {
        allPlayers = new LinkedList<Player>();
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

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.out.println("start called.");
        primaryStage.setTitle("Chain Reaction");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/chainReactionIcon.png")));


        int numberOfPlayers;
        System.out.println("Enter number of players.");
        Scanner scanner = new Scanner(System.in);
        numberOfPlayers = scanner.nextInt();
        int i=0;

        while(i<numberOfPlayers)
        {
            Player player = new Player(i, true);
            allPlayers.add(player); //adding the player to the game
            i++;
        }
        System.out.println("Enter X and Y.");
        int x,y;
        x=scanner.nextInt();
        y=scanner.nextInt();
//        Matrix gameMatrix=new Matrix(x, y);
//        setGameMatrix(gameMatrix);

        BooleanProperty[][] switches = new BooleanProperty[x][y];
        for (i=0;i<x;i++)
        {
            for (int j=0;j<y;j++)
            {
                switches[i][j]=new SimpleBooleanProperty();
            }
        }

        gridPane=createGrid(switches);
        StackPane root=new StackPane(gridPane.getGridPane());
        Scene scene=new Scene(root, (x*40)+100, (y*40)+100, Color.AZURE);
        scene.getStylesheets().add("./Stylesheets/grid-with-borders.css");



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

















        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Color getColor(Player player)
    {
        switch(player.getPlayerColour())
        {
            case 0: return Color.VIOLET;
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.YELLOW;
            case 4: return Color.ORANGE;
            case 5: return Color.RED;
            case 6: return Color.BROWN;
            case 7: return Color.BLACK;
            default: return null;
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

        ExtendedCell cell = new ExtendedCell(new StackPane(), x, y, null, new ArrayList<ExtendedCell>());

        Group group=new Group();

        cell.getCell().setOnMouseClicked(e ->
        {
            if (!cellSwitch.get())
                cellSwitch.set(!cellSwitch.get());
            try
            {
                System.out.println(x+" "+y);
                Sphere sphere = new Sphere(10);

                Player curPlayer=allPlayers.peek();
                System.out.println("Chance of player with " + curPlayer.getPlayerColourByString());
                Cell cellSelected = gameMatrix.getCellFromCoordinate(x,y);

                if (cellSelected.isCellOccupied())
                {
                    int curCellColor = cellSelected.getPlayerOccupiedBy().getPlayerColour(); //there are some balls existing there
                    if (curCellColor == curPlayer.getPlayerColour())
                    { // check if player is adding to his color
                        //add ball function
                        cellSelected.addBall(curPlayer);
                        allPlayers.remove(curPlayer);
                    }
                    else
                    {  //if not
                        //show error, wrong move
                        //we should not remove the player from the queue
                        System.out.println("can't put ball here.already occupied by "+cellSelected.getPlayerOccupiedBy().getPlayerColourByString()+" player."+clickedXPos+clickedYPos);
                    }
                }
                else
                {
                    cellSelected.addBall(curPlayer);
                    allPlayers.remove(curPlayer);
                }

                for (Player randomPlayer : allPlayers)
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


                if (curPlayer.isAlive())
                {
                    allPlayers.add(curPlayer);
                }

                gameMatrix.printMatrix();


                PhongMaterial phongMaterial = new PhongMaterial();
                phongMaterial.setDiffuseColor(getColor(curPlayer));
                phongMaterial.setSpecularColor(Color.BLACK);
                sphere.setMaterial(phongMaterial);
                switch (group.getChildren().size()) {
                    case 0:
                        sphere.setTranslateX(0);
                        break;

                    case 1:
                        sphere.setTranslateX(10);
                        break;

                    case 2:
                        sphere.setTranslateY(10);
                        sphere.setTranslateX(5);
                        break;

                    default:
                        break;
                }
                group.getChildren().add(sphere);
                if(cell.getCell().getChildren().size()==0)
                    cell.getCell().getChildren().add(group);
                //cell.getCell().getChildren().forEach(l -> System.out.println(l.toString()));
            }
            catch(IndexOutOfBoundsException e1)
            {
                e1.printStackTrace();
                System.out.println(allPlayers.peek());
            }
            catch(Exception e2)
            {
                e2.printStackTrace();
            }

        });

        cell.getCell().getStyleClass().add("cell");
        return cell;
    }

    private static ExtendedGrid createGrid(BooleanProperty[][] switches)
    {

        int numCols=switches.length;
        int numRows=switches[0].length;

        GridPane grid=new GridPane();
        ExtendedGrid returnGrid=new ExtendedGrid(null, null, switches.length, switches[0].length);
        ArrayList<ExtendedCell> returnExtendedCells=new ArrayList<ExtendedCell>();

        for (int x=0;x<numCols;x++)
        {
            ColumnConstraints cc=new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        for (int y=0;y<numRows;y++)
        {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }

        for (int x=0;x<numCols;x++)
        {
            for (int y=0;y<numRows;y++)
            {
                ExtendedCell cell=createCell(switches[x][y], x, numRows-y-1);
                returnExtendedCells.add(cell);
                grid.add(cell.getCell(), x, y);
            }
        }

        grid.getStyleClass().add("grid");
        returnGrid.setGridPane(grid);
        returnGrid.setExtendedCells(returnExtendedCells);
        return returnGrid;
    }

    public void setGameMatrix(Matrix gameMatrix)
    {
        this.gameMatrix = gameMatrix;
    }
}
