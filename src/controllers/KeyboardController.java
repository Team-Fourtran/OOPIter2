package controllers;


import application.Game;
import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Iterators.AssetIterator;

public class KeyboardController {
    MessageGenerator msgGen;
    private Game game;

    public KeyboardController(Game game, KeyPressInformer keyInformer, AssetIterator assIter, TileTargetting tt){
        //Initializes the message generator, setting itself as the reciever, and forwarding the keysPressedList
        this.msgGen = new MessageGenerator(this, keyInformer, assIter, tt);
        this.game = game;
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
}

