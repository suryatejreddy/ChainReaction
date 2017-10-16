package sample;

import NonUIComponents.Matrix;
import NonUIComponents.Player;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application
{
    private static int dimX;
    private static int dimY;
    public static ExtendedGrid gridPane;    //UI grid for entire grid.
    public static Scanner scanner;
    public static Matrix gameMatrix;        //Non UI grid.

    public static void setGameMatrix(Matrix matrix)
    {
        gameMatrix = matrix;
    }

    public static void setScanner(Scanner scan)
    {
        scanner = scan;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Chain Reaction");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/chainReactionIcon.png")));
        ChainReaction.main(null);
        BooleanProperty[][] switches = new BooleanProperty[dimX][dimY];
        for (int i=0;i<dimX;i++)
        {
            for (int j=0;j<dimY;j++)
            {
                switches[i][j]=new SimpleBooleanProperty();
            }
        }

        gridPane=createGrid(switches);
        StackPane root=new StackPane(gridPane.getGridPane());
        Scene scene=new Scene(root, (dimX*40)+100, (dimY*40)+100, Color.AZURE);
        scene.getStylesheets().add("./Stylesheets/grid-with-borders.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setDimensions(int x, int y)
    {
        dimX=x;
        dimY=y;
    }

    public static void setCellClicker(Player player, int x, int y)
    {
        for(ExtendedCell cell: gridPane.getExtendedCells())
        {
            cell.getCell().setOnMouseClicked(e ->
            {

            });
        }
    }

    private static ExtendedCell createCell(BooleanProperty cellSwitch, int x, int y)
    {

        ExtendedCell cell = new ExtendedCell(new StackPane(), x, y);

        Group group=new Group();

        cell.getCell().setOnMouseClicked(e ->
        {
            if (!cellSwitch.get())
                cellSwitch.set(!cellSwitch.get());
            try
            {
                Sphere sphere = new Sphere(10);

                PhongMaterial phongMaterial = new PhongMaterial();
                phongMaterial.setDiffuseColor(Color.VIOLET);
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
                cell.getCell().getChildren().add(group);
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
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
        ExtendedGrid returnGrid=new ExtendedGrid(null, null);
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
                ExtendedCell cell=createCell(switches[x][y], x, y);
                returnExtendedCells.add(cell);
                grid.add(cell.getCell(), x, y);
            }
        }

        System.out.println("");
        for(int x=0;x<grid.getChildren().size();x++)
        {
            System.out.println(grid.getChildren().get(x));
        }

        grid.getStyleClass().add("grid");
        returnGrid.setGridPane(grid);
        returnGrid.setExtendedCells(returnExtendedCells);
        return returnGrid;
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
