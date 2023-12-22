package at.ac.fhcampuswien.sleepwalker;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Sleepwalker extends Application {
    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        String windowTitle = GameProperties.TITLE + " v" + GameProperties.GAME_VERSION;
        primaryStage.setTitle(windowTitle);
        primaryStage.setResizable(false);
        GameManager.getInstance(primaryStage).showMainMenu();
    }
}