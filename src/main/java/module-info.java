module at.ac.fhcampuswien.sleepwalker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens at.ac.fhcampuswien.sleepwalker to javafx.fxml;
    exports at.ac.fhcampuswien.sleepwalker;
    exports at.ac.fhcampuswien.sleepwalker.controller;
    opens at.ac.fhcampuswien.sleepwalker.controller to javafx.fxml;
    exports at.ac.fhcampuswien.sleepwalker.exceptions;
    opens at.ac.fhcampuswien.sleepwalker.exceptions to javafx.fxml;
}