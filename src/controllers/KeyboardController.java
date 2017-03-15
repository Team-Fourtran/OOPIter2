package controllers;


import application.Game;
import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Iterators.AssetIterator;
import views.MainScreen;

public class KeyboardController {
    MessageGenerator msgGen;
    private Game game;
    private MainScreen mainScreen;

    public KeyboardController(Game game, MainScreen mainScreen, AssetIterator assIter){
        //Initializes the message generator, setting itself as the reciever, and forwarding the keysPressedList
        this.msgGen = new MessageGenerator(this, mainScreen.getKeyInformer(), assIter, mainScreen.getTileTargetter());
        this.game = game;
        this.mainScreen = mainScreen;
    }

    /* Happens when the player changes (aka turn switching) */
    public void updateIterator(AssetIterator assIter){
        this.msgGen.updateIterator(assIter);
    }

    /* TODO: For now, just prints messages that it receives */
    protected void handleMsg(CTRLCommand cmd){
        System.out.print(cmd.toString());
        game.notifyOfCommand(cmd);
    }

    public void updateCommands(AssetIterator iter){
//        mainScreen.updateControls(iter);
    }
}

