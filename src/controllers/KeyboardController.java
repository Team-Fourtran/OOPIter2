package controllers;


import models.playerAsset.AssetIterator;
import models.playerAsset.CommandIterator;

import java.util.HashMap;
import java.util.Iterator;

public class KeyboardController {
    MessageGenerator msgGen;

    public KeyboardController(KeyPressInformer keyInformer, AssetIterator assIter, CommandIterator cmdIter){
        //Initializes the message generator, setting itself as the reciever, and forwarding the keysPressedList
        this.msgGen = new MessageGenerator(this, keyInformer, assIter, cmdIter);
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

