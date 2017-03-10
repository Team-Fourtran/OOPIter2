package controllers;

import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.playerAsset.Assets.*;

import java.util.HashMap;

class MessageGenerator implements KeyPressListener{

    protected AssetIterator assetIterator;

    private KeyboardController receiver;

    State currentState;

    MessageGenerator(KeyboardController receiver, KeyPressInformer keyInformer, AssetIterator assIter){
        this.receiver = receiver;                   //Set up who will receive Commands once they're generated
        keyInformer.registerClient(this);    //Register self to get keypress notifications from the keyInformer
        this.assetIterator = assIter;               //Set up the asset iterator
        assetIterator.first();
        /* Initialize the State object */
        currentState = new State(assetIterator.getCurrentMode(), assetIterator.getCurrentType(), (PlayerAsset) assetIterator.getElement());
    }

    private void generateMessage(){
        //Sends receiver a message as generated by the current Mode
        /*
        PlayerAsset pa = (PlayerAsset)assetIterator.getElement();;
        String disMsg = "[" + pa.getID() + "]\t(" + pa.toString() + ")";
        receiver.handleMsg(disMsg);
        */
        System.out.println("Pressed Enter");
    }

    @Override //Listen to notifications from a KeyPressInformer
    public void updateKeysPressed(HashMap<String, Boolean> kp) {
        interpretKeystrokes(kp);
    }

    /* Updates the State object based on the keystrokes detected */
    private void interpretKeystrokes(HashMap<String, Boolean> keystrokes){
        if(keystrokes.get("ENTER")){
            generateMessage();
        }

        /* Keypress combinations with CONTROL+[some key] cycle MODE or TYPE */
        if(keystrokes.get("CONTROL")){

            /* CONTROL+{UP/DOWN}: Cycle MODE */
            if(keystrokes.get("UP")){
                assetIterator.prevMode();           //CONTROL+UP: Previous Mode
            } else if(keystrokes.get("DOWN")){
                assetIterator.nextMode();           //CONTROL+DOWN: Next Mode
            }

            /* CONTROL+{LEFT/RIGHT}: Cycle TYPE */
            else if(keystrokes.get("LEFT")){        //CONTROL+LEFT: Previous Type
                assetIterator.prevType();
            } else if(keystrokes.get("RIGHT")){     //CONTROL+RIGHT: Next Type
                assetIterator.nextType();
            }

        }

        /* Keypresses without control cycle TYPE INSTANCES and COMMANDS */
        else {

            //LEFT/RIGHT: Cycle Type Instances
            if(keystrokes.get("LEFT")){
                assetIterator.prev();

            } else if(keystrokes.get("RIGHT")){
                assetIterator.next();
            }

            /* UP/DOWN: Cycle Commands */
            else if(keystrokes.get("UP")){               /* Previous command */
                //TODO: this
                System.out.println("Pressed up");

            } else if(keystrokes.get("DOWN")){      /* Next command */
                //TODO: this
                System.out.println("Pressed down");

            }
        }

        /* Update the State based on the new Iterator info */
        currentState.setMode(assetIterator.getCurrentMode());
        currentState.setType(assetIterator.getCurrentType());
        currentState.setInstance((PlayerAsset)assetIterator.getElement());
    }

    //Gets called when player turn switches. Changes the iterator on hand.
    protected void updateIterator(AssetIterator assetIterator){
        this.assetIterator = assetIterator;
    }

}

class State{
    String currentMode;
    String currentType;
    PlayerAsset currentInstance;

    State(String m, String t, PlayerAsset i){
        this.currentMode = m;
        this.currentType = t;
        this.currentInstance = i;
    }

    protected String getMode(){
        return this.currentMode;
    }
    protected String getType(){
        return this.currentType;
    }
    protected PlayerAsset getInstance(){
        return this.currentInstance;
    }

    protected void setMode(String mode){
        this.currentMode = mode;
    }
    protected void setType(String type){
        this.currentType = type;
    }
    protected void setInstance(PlayerAsset instance){
        this.currentInstance = instance;
    }
}