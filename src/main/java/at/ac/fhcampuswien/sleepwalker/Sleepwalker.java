package at.ac.fhcampuswien.sleepwalker;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Sleepwalker extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException{
        GameObjects.stageRoot = primaryStage;
        GameObjects.loadMainMenu();
    }

    public static void main(String[] args){
        launch();
    }
}