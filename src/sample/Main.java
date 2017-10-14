package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/chainReactionIcon.png")));
        Sphere sphere=new Sphere();
        sphere.setRadius(50);
        sphere.setTranslateX(100);
        sphere.setTranslateY(150);
        sphere.setCullFace(CullFace.BACK);
        Group root1=new Group(sphere);
        Scene scene=new Scene(root1, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
        String musicFile = "./AudioFiles/gameIntro.wav";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();

        musicFile="./AudioFiles/gameOver.wav";
        sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
