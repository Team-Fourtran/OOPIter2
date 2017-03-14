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

        PlayerAsset tempAsset = (PlayerAsset) assetIterator.getElement();
        tempAsset.accept(cmdGetter);
        cmdIter = cmdGetter.getIterator();
        /* Initialize the State object */
        currentState = new State(
                this,
                assetIterator.getCurrentMode(),
                assetIterator.getCurrentType(),
                tempAsset,
                cmdIter.first()
        );
    }

    @Override //Listen to notifications from a KeyPressInformer
    public void updateKeysPressed(HashMap<String, Boolean> kp) {
        interpretKeystrokes(kp);
    }

    /* Updates the State object based on the keystrokes detected */
    private void interpretKeystrokes(HashMap<String, Boolean> keystrokes){
        if(keystrokes.get("ENTER")){
            printStatus();
            dispatchCommandForConfig();
            return;
        }

        /* Keypress combinations with CONTROL+[some key] cycle MODE or TYPE */

        /* CONTROL+{UP/DOWN}: Cycle MODE */
        else if(keystrokes.get("CONTROL") && keystrokes.get("UP")){
            assetIterator.prevMode();           //CONTROL+UP: Previous Mode
        } else if(keystrokes.get("CONTROL") && keystrokes.get("DOWN")){
            assetIterator.nextMode();           //CONTROL+DOWN: Next Mode
        }

        /* CONTROL+{LEFT/RIGHT}: Cycle TYPE */
        else if(keystrokes.get("CONTROL") && keystrokes.get("LEFT")){        //CONTROL+LEFT: Previous Type
            System.out.println("Going to previous type");
            assetIterator.prevType();
        } else if(keystrokes.get("CONTROL") && keystrokes.get("RIGHT")){     //CONTROL+RIGHT: Next Type
            System.out.println("Going to next type");
            assetIterator.nextType();
        }

        /* Keypresses without control cycle TYPE INSTANCES and COMMANDS */

        //LEFT/RIGHT: Cycle Type Instances
        else if(!(keystrokes.get("CONTROL")) && keystrokes.get("LEFT")){
            assetIterator.prev();

        } else if(!(keystrokes.get("CONTROL")) && keystrokes.get("RIGHT")){
            assetIterator.next();
        }

        /* UP/DOWN: Cycle Commands */
        else if(!(keystrokes.get("CONTROL")) && keystrokes.get("UP")){               /* Previous command */
            cmdIter.prev();
        } else if(!(keystrokes.get("CONTROL")) && keystrokes.get("DOWN")){      /* Next command */
            cmdIter.next();

        }

        /* Update the State based on the new Iterator info */
        currentState.setMode(assetIterator.getCurrentMode());
        currentState.setType(assetIterator.getCurrentType());
        currentState.setInstance((PlayerAsset)assetIterator.getElement());
        updateCommandList();

        printStatus();
    }

    private void updateCommandList(){
        /* Update the CommandIterator */
        CommandListVisitor cmdGetter = new CommandListVisitor();
        ((PlayerAsset) assetIterator.getElement()).accept(cmdGetter);
        cmdIter = cmdGetter.getIterator();
        currentState.setCmd(cmdIter.current());
    }

    private void printStatus(){
        System.out.println("STATUS:     "+currentState.getMode() + " | "+currentState.getType()+" | "+
        currentState.getInstance().toString()+" | "+currentState.getCmd().toString());
    }

    //Gets called when player turn switches. Changes the iterator on hand.
    protected void updateIterator(AssetIterator assetIterator){
        this.assetIterator = assetIterator;
    }

    private void dispatchCommandForConfig(){
        try {
            System.out.println("Command requesting config: " + currentState.getCmd().hashCode());
            currentState.getCmd().configure(currentState);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void receiveConfiguredCmd(CTRLCommand cmd){
        if(cmd.isConfigured())
            receiver.handleMsg(cmd);   /* Send it to the KeyboardController */
        else System.out.println("Command wasn't configured properly");
        updateCommandList();    //So that the list of commands is updated before the next one can be issued
    }

    /* Auxillary functions to request additional information for configuring Commands */

    public void requestTile(PlayerAsset initialHighlightAsset){
        System.out.println("MessageGenerator requesting tile. Callback is " + currentState.getCmd().hashCode());
        tileTargetter.targetTile(this, initialHighlightAsset);
    }

    public void receiveTargetTile(TileAssociation cbTile){
        System.out.println("MessageGenerator received tile. Callback is " + currentState.getCmd().hashCode());
        currentState.setDestinationTile(cbTile);
    }
}

class State implements CommandComponents{
    /* Acts as an internal structure to encapsulate the MessageGenerator's state, but also as an implementation of a
     * public interface to pass around the components for configureing a Command object */
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
    public void requestDestinationTile(CTRLCommand callbackObject) {
        this.currentCommand = callbackObject;   //Set the current command so that we may call back to it
        System.out.println("Requesting requestTile(). Set callback CTRLCommand to " + callbackObject.hashCode());
        this.destinationTile = null;            //Reset the destination tile to remove ambiguity.
        msgGen.requestTile(currentInstance);    //Tell the MessageGenerator to initiate the tile request process
        //The method below (setDestinationTile) gets called once the tile is ready.
    }

    //This gets called when the Tile request comes through
    protected void setDestinationTile(TileAssociation t){
        System.out.println("In State: Got tilestate back: " + t + "\nCalling back to CTRLCommand " + currentCommand.hashCode());
        this.destinationTile = t;       //Update the destination tile
        try {currentCommand.callback();} catch(Exception e){e.printStackTrace();}
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