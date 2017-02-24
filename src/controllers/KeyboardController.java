package controllers;

import java.util.HashMap;
import java.util.Iterator;

public class KeyboardController {
    MessageGenerator msgGen;

    public KeyboardController(KeyPressInformer keyInformer, HashMap<String, Iterator> assetIterators){
        //Initializes the message generator, setting itself as the reciever, and forwarding the keysPressedList
        this.msgGen = new MessageGenerator(this, keyInformer, assetIterators);
    }

    /* Happens when the player changes (aka turn switching) */
    public void updateIterators(HashMap<String, Iterator> assetIterators){
        this.msgGen.updateIterators(assetIterators);
    }

    /* TODO: For now, just prints messages that it receives */
    protected void handleMsg(String msg){
        System.out.println("From " + this + ":\n\tReceived message from MsgGen:\n\t\t" + msg);
    }
}