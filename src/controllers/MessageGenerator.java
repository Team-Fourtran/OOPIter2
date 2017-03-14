package controllers;

import models.assetOwnership.TileAssociation;
import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.playerAsset.Assets.*;
import models.visitor.CommandListVisitor;

import java.util.HashMap;

class MessageGenerator implements KeyPressListener{

    private AssetIterator assetIterator;  //Used to iterate through current Player's assets
    private KeyboardController receiver;
    private TileTargetting tileTargetter;
    private CommandIterator cmdIter;

    State currentState;

    MessageGenerator(KeyboardController receiver, KeyPressInformer keyInformer, AssetIterator assIter, TileTargetting tt){
        this.receiver = receiver;                   //Set up who will receive Commands once they're generated

        keyInformer.registerClient(this);    //Register self to get keypress notifications from the keyInformer

        tileTargetter = tt;                         //Used for querying the game map for a target tile
        this.assetIterator = assIter;               //Set up the asset iterator
        assetIterator.first();

        CommandListVisitor cmdGetter = new CommandListVisitor();  //Set up the CommandList visitor

        PlayerAsset tempAsset = (PlayerAsset) assetIterator.current();
        tempAsset.accept(cmdGetter);
        cmdIter = cmdGetter.getIterator();
        /* Initialize the State object */
        currentState = new State(
                this,
                assetIterator.getCurrentMode(),
                assetIterator.getElement(), //get type
                tempAsset,
                cmdIter.first()
        );
    }

    @Override //Listen to notifications from a KeyPressInformer
    public void updateKeysPressed(HashMap<String, Boolean> kp) {
        interpretKeystrokes(kp);
        printStatus();
    }

    /* Updates the State object based on the keystrokes detected */
    private void interpretKeystrokes(HashMap<String, Boolean> keystrokes){
        if(keystrokes.get("ENTER")){
            dispatchCommandForConfig(currentState.getCmd());
            updateCommandList();
        }

        /* Keypress combinations with CONTROL+[some key] cycle MODE or TYPE */

        /* CONTROL+{UP/DOWN}: Cycle MODE */
        else if(keystrokes.get("CONTROL") && keystrokes.get("UP")){
            assetIterator.prev();           //CONTROL+UP: Previous Mode
            updateCommandList();
        } else if(keystrokes.get("CONTROL") && keystrokes.get("DOWN")){
            assetIterator.next();           //CONTROL+DOWN: Next Mode
            updateCommandList();
        }

        /* CONTROL+{LEFT/RIGHT}: Cycle TYPE */
        else if(keystrokes.get("CONTROL") && keystrokes.get("LEFT")){        //CONTROL+LEFT: Previous Type
            assetIterator.prevType();
            updateCommandList();
        } else if(keystrokes.get("CONTROL") && keystrokes.get("RIGHT")){     //CONTROL+RIGHT: Next Type
            assetIterator.nextType();
            updateCommandList();
        }

        /* Keypresses without control cycle TYPE INSTANCES and COMMANDS */

        //LEFT/RIGHT: Cycle Type Instances
        else if(!(keystrokes.get("CONTROL")) && keystrokes.get("LEFT")){
            assetIterator.prevInstance();
            updateCommandList();

        } else if(!(keystrokes.get("CONTROL")) && keystrokes.get("RIGHT")){
            assetIterator.nextInstance();
            updateCommandList();
        }

        /* UP/DOWN: Cycle Commands */
        else if(!(keystrokes.get("CONTROL")) && keystrokes.get("UP")){               /* Previous command */
            cmdIter.prev();
            currentState.setCmd(cmdIter.current());
        } else if(!(keystrokes.get("CONTROL")) && keystrokes.get("DOWN")){      /* Next command */
            cmdIter.next();
            currentState.setCmd(cmdIter.current());
        }

        /* Update the State based on the new Iterator info */
        currentState.setMode(assetIterator.getCurrentMode());
        currentState.setType(assetIterator.getElement());
        currentState.setInstance((PlayerAsset)assetIterator.current());

    }

    private void updateCommandList(){
        /* Update the CommandIterator */
        CommandListVisitor cmdGetter = new CommandListVisitor();
        ((PlayerAsset) assetIterator.current()).accept(cmdGetter);
        cmdIter = cmdGetter.getIterator();
        currentState.setCmd(cmdIter.current());
    }

    private void printStatus(){
        System.out.println("STATUS:     "+currentState.getMode() + " | "+currentState.getType()+" | "+currentState.getInstance().toString()+" | "+currentState.getCmd().toString());
    }

    //Gets called when player turn switches. Changes the iterator on hand.
    protected void updateIterator(AssetIterator assetIterator){
        this.assetIterator = assetIterator;
    }

    private void dispatchCommandForConfig(CTRLCommand thisCmd){
        try {
            thisCmd.configure(currentState);
        } catch(Exception e){
            e.printStackTrace();
        }
        receiveConfiguredCmd(thisCmd);
    }

    public void receiveConfiguredCmd(CTRLCommand cmd){
        if(cmd.isConfigured())
            receiver.handleMsg(cmd);   /* Send it to the KeyboardController */
        else System.out.println("Command wasn't configured properly");
    }

    /* Auxillary functions to request additional information for configuring Commands */

    public void requestTile(PlayerAsset initialHighlightAsset){
        tileTargetter.targetTile(this, initialHighlightAsset);
    }

    public void receiveTargetTile(TileAssociation cbTile){
        currentState.setDestinationTile(cbTile);
    }
}

class State implements CommandComponents{
    /* Acts as an internal structure to encapsulate the MessageGenerator's state, but also as an implementation of a
     * public interface to pass around the components for configuring a Command object */
    private String currentMode;
    private String currentType;
    private PlayerAsset currentInstance;
    private CTRLCommand currentCommand;
    private MessageGenerator msgGen;
    private TileAssociation destinationTile;

    State(MessageGenerator msgGen, String m, String t, PlayerAsset i, CTRLCommand c){
        this.msgGen = msgGen;
        this.currentMode = m;
        this.currentType = t;
        this.currentInstance = i;
        this.currentCommand = c;
    }

    /* State-specific methods */
    protected String getMode(){return this.currentMode;}
    protected String getType(){return this.currentType;}
    protected PlayerAsset getInstance(){return this.currentInstance;}
    protected CTRLCommand getCmd(){return this.currentCommand;}


    protected void setMode(String mode){this.currentMode = mode;}
    protected void setType(String type){this.currentType = type;}
    protected void setInstance(PlayerAsset instance){this.currentInstance = instance;}
    protected void setCmd(CTRLCommand cmd){this.currentCommand = cmd;}

    /* CommandComponents overrides */

    @Override   //Called by CTRLCommands once they think they're ready to be executed.
    public void requestExecution(){
        this.msgGen.receiveConfiguredCmd(this.currentCommand);
    }
    @Override
    public PlayerAsset getRequestingAsset() {return this.getInstance();}

    @Override
    //TODO: This
    public PlayerAsset getTargetAsset() {return null;}

    @Override
    //TODO: This
    public TileAssociation getRequestingTile() {return null;}

    @Override
    public void requestDestinationTile() {
        this.destinationTile = null;            //Reset the destination tile to remove ambiguity.
        msgGen.requestTile(currentInstance);    //Tell the MessageGenerator to initiate the tile request process
        //The method below (setDestinationTile) gets called once the tile is ready.
    }

    //This gets called when the Tile request comes through
    protected void setDestinationTile(TileAssociation t){
        this.destinationTile = t;       //Update the destination tile
//        try {currentCommand.queryAgain();} catch(Exception e){e.printStackTrace();}
        //Tell the current command to query again, signaling that the tile is done.
    }

    @Override
    //Actually return the destination tile
    public TileAssociation getDestinationTile() {
        return this.destinationTile;
    }

    @Override
    //TODO: This
    public Player getOpposingPlayer() {return null;}

    @Override
    //TODO: This
    public String getString() {return null;}

    @Override
    //TODO: This
    public int getInt() {return 0;}

    @Override
    //TODO: This
    public Unit[] getUnitList() {return new Unit[0];}
}