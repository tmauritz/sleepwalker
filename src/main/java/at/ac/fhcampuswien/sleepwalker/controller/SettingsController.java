package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;

public class SettingsController {

    public void handleBackToMainMenu() {
        GameManager.getInstance().showMainMenu();
    }
}

