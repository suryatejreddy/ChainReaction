package sample;

import NonUIComponents.Game;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application
{
    private static int dimX;
    private static int dimY;

    @Override
    public void start(Stage primaryStage) throws Exception
    {



        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/chainReactionIcon.png")));
        Game.main(null);

        BooleanProperty[][] switches = new BooleanProperty[dimX][dimY];
        for (int i=0;i<dimX;i++)
        {
            for (int j=0;j<dimY;j++)
            {
                switches[i][j]=new SimpleBooleanProperty();
            }
        }

        GridPane gridPane=createGrid(switches);
        StackPane root=new StackPane(gridPane);
        Scene scene=new Scene(root, (dimX*40)+100, (dimY*40)+100, Color.GOLDENROD);
        scene.getStylesheets().add("./Stylesheets/grid-with-borders.css");
        primaryStage.setScene(scene);
        primaryStage.show();




//        String musicFile = "./AudioFiles/gameIntro.wav";     // For example
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        //mediaPlayer.play();
//
//        musicFile="./AudioFiles/gameOver.wav";
//        sound = new Media(new File(musicFile).toURI().toString());
//        mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
    }

    public static void setDimensions(int x, int y)
    {
        dimX=x;
        dimY=y;
    }

    private StackPane createCell(BooleanProperty cellSwitch) {

        StackPane cell = new StackPane();

        cell.setOnMouseClicked(e -> cellSwitch.set(! cellSwitch.get() ));

        Sphere circle = new Sphere(10);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.VIOLET);
        material.setSpecularColor(Color.BLACK);
        circle.setMaterial(material);

        circle.visibleProperty().bind(cellSwitch);

        cell.getChildren().add(circle);
        cell.getStyleClass().add("cell");
        return cell;
    }

    private GridPane createGrid(BooleanProperty[][] switches) {

        int numCols = switches.length ;
        int numRows = switches[0].length ;

        GridPane grid = new GridPane();

        for (int x = 0 ; x < numCols ; x++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < numRows ; y++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(rc);
        }

        for (int x = 0 ; x < numCols ; x++) {
            for (int y = 0 ; y < numRows ; y++) {
                grid.add(createCell(switches[x][y]), x, y);
            }
        }

        grid.getStyleClass().add("grid");
        return grid;
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
