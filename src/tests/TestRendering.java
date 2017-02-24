package tests;

import views.MainScreen;

public class TestRendering {
    public static void main(String[] args){
        MainScreen mainScreen = new MainScreen();
		mainScreen.initialize();
		mainScreen.generateMainScreen();
		mainScreen.showMainScreen();
    }
}

