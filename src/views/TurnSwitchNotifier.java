package views;

import application.Game;

public class TurnSwitchNotifier {
    Game game;  //The game that will be notified

    public void addListener(Game game){
        this.game = game;
    }

    public void notifyOfTurnSwitch(){
        if(null != this.game)
            this.game.turnSwitch();
        else System.out.println("No Game object is registered to listen to turn switches");
    }
}
