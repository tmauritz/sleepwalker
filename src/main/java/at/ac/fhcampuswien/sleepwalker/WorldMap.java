package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WorldMap extends Stage {
    public WorldMap(){
        Button backToMainMenu = new Button("Back to Main Menu");
        Pane x = new AnchorPane(backToMainMenu);
        backToMainMenu.setOnAction(t -> GameObjects.loadMainMenu()); //Look Mum, I'm using Lambdas!
        this.setScene(new Scene(x, GameProperties.WIDTH, GameProperties.HEIGHT));
    }

    //TODO: Add Levels for selection

}
