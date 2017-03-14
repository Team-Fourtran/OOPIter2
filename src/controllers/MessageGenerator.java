package controllers;

import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.playerAsset.Assets.*;
import models.visitor.CommandListVisitor;

import java.util.HashMap;

class MessageGenerator implements KeyPressListener{

    private AssetIterator assetIterator;  //Used to iterate through current Player's assets
    private CommandListVisitor cmdGetter;
    private KeyboardController receiver;

    State currentState;

    MessageGenerator(KeyboardController receiver, KeyPressInformer keyInformer, AssetIterator assIter){
        this.receiver = receiver;                   //Set up who will receive Commands once they're generated

        keyInformer.registerClient(this);    //Register self to get keypress notifications from the keyInformer

        this.assetIterator = assIter;               //Set up the asset iterator
        assetIterator.first();

        this.cmdGetter = new CommandListVisitor();  //Set up the CommandList visitor

        PlayerAsset tempAsset = (PlayerAsset) assetIterator.getElement();
        tempAsset.accept(cmdGetter);
        CommandIterator tempIter = cmdGetter.getIterator();
        /* Initialize the State object */
        currentState = new State(
                assetIterator.getCurrentMode(),
                assetIterator.getCurrentType(),
                tempAsset,
                tempIter,
                tempIter.first()
        );
    }

    private void generateMessage(){
        //Sends receiver a message as generated by the current Mode
        String theMessage = "Mode: " + currentState.getMode() + "\tType: " + currentState.getType();
        theMessage += "\tAsset: " + currentState.getInstance() + "\tCommand: " + currentState.getCmd() + "\n";
        receiver.handleMsg(theMessage);
        //System.out.println("Pressed Enter");
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
                currentState.getCmdIter().prev();
            } else if(keystrokes.get("DOWN")){      /* Next command */
                currentState.getCmdIter().next();
            }
        }

        /* Update the State based on the new Iterator info */
        currentState.setMode(assetIterator.getCurrentMode());
        currentState.setType(assetIterator.getCurrentType());
        currentState.setInstance((PlayerAsset)assetIterator.getElement());
        currentState.setCmd(currentState.getCmdIter().current());

    }

    //Gets called when player turn switches. Changes the iterator on hand.
    protected void updateIterator(AssetIterator assetIterator){
        this.assetIterator = assetIterator;
    }
}

class State{
    private String currentMode;
    private String currentType;
    private PlayerAsset currentInstance;
    private CommandIterator currentCmdIter;
    private CTRLCommand currentCommand;

    State(String m, String t, PlayerAsset i, CommandIterator ci, CTRLCommand c){
        this.currentMode = m;
        this.currentType = t;
        this.currentInstance = i;
        this.currentCmdIter = ci;
        this.currentCommand = c;
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
    protected CommandIterator getCmdIter(){
        return this.currentCmdIter;
    }
    protected CTRLCommand getCmd(){
        return this.currentCommand;
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
    protected void setCmdIter(CommandIterator iter){
        this.currentCmdIter = iter;
    }
    protected void setCmd(CTRLCommand cmd){
        this.currentCommand = cmd;
    }
}