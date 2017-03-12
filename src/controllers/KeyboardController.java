package controllers;


import models.playerAsset.Iterators.AssetIterator;

public class KeyboardController {
    MessageGenerator msgGen;

    public KeyboardController(KeyPressInformer keyInformer, AssetIterator assIter, TileTargetting tt){
        //Initializes the message generator, setting itself as the reciever, and forwarding the keysPressedList
        this.msgGen = new MessageGenerator(this, keyInformer, assIter, tt);
    }

    /* Happens when the player changes (aka turn switching) */
    public void updateIterator(AssetIterator assIter){
        this.msgGen.updateIterator(assIter);
    }

    /* TODO: For now, just prints messages that it receives */
    protected void handleMsg(String msg){
        System.out.print(msg);
    }
}

