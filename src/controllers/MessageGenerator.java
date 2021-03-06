package controllers;

import models.assetOwnership.TileAssociation;
import models.ctrlCommand.CTRLCommand;
import models.ctrlCommand.CommandNotConfiguredException;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Assets.*;
import views.*;

import java.util.HashMap;

class MessageGenerator implements KeyPressListener {

    private AssetIterator assetIterator;  //Used to iterate through current Player's assets
    private KeyboardController receiver;
    private TileTargetting tileTargetter;
    private AssetTargetting assetTargetter;

    State currentState;

    MessageGenerator(KeyboardController receiver, KeyPressInformer keyInformer, AssetIterator assIter, TileTargetting tt, AssetTargetting at) {
        this.receiver = receiver;                   //Set up who will receive Commands once they're generated

        keyInformer.registerClient(this);    //Register self to get keypress notifications from the keyInformer
        assetTargetter = at;
        tileTargetter = tt;                         //Used for querying the game map for a target tile
        this.assetIterator = assIter;               //Set up the asset iterator
        assetIterator.first();

        /* Initialize the State object */
        currentState = new State(
                this,
                assetIterator.getCurrentMode(),
                assetIterator.getElement(), //get type
                (PlayerAsset) assetIterator.current()
        );
    }

    @Override //Listen to notifications from a KeyPressInformer
    public void updateKeysPressed(HashMap<String, Boolean> kp) {
        interpretKeystrokes(kp);
    }

    /* Updates the State object based on the keystrokes detected */
    private void interpretKeystrokes(HashMap<String, Boolean> keystrokes) {
        if (keystrokes.get("ENTER")) {
            currentState.setCmd(assetIterator.getCommand());
            dispatchCommandForConfig(assetIterator.getCommand());
            keystrokes.replace("ENTER", false);
        }

        /* Keypress combinations with CONTROL+[some key] cycle MODE or TYPE */

        /* CONTROL+{UP/DOWN}: Cycle MODE */
        else if (keystrokes.get("CONTROL") && keystrokes.get("UP")) {
            assetIterator.prev();           //CONTROL+UP: Previous Mode
        } else if (keystrokes.get("CONTROL") && keystrokes.get("DOWN")) {
            assetIterator.next();           //CONTROL+DOWN: Next Mode
        }

        /* CONTROL+{LEFT/RIGHT}: Cycle TYPE */

        else if (keystrokes.get("CONTROL") && keystrokes.get("LEFT")) {        //CONTROL+LEFT: Previous Type
            assetIterator.prevType();
        } else if (keystrokes.get("CONTROL") && keystrokes.get("RIGHT")) {     //CONTROL+RIGHT: Next Type
            assetIterator.nextType();
        }

        /* Keypresses without control cycle TYPE INSTANCES and COMMANDS */

        //LEFT/RIGHT: Cycle Type Instances
        else if (!(keystrokes.get("CONTROL")) && keystrokes.get("LEFT")) {
            assetIterator.prevInstance();
        } else if (!(keystrokes.get("CONTROL")) && keystrokes.get("RIGHT")) {
            assetIterator.nextInstance();
        }

        /* UP/DOWN: Cycle Commands */
        else if (!(keystrokes.get("CONTROL")) && keystrokes.get("UP")) {               /* Previous command */
            assetIterator.prevCommand();
        } else if (!(keystrokes.get("CONTROL")) && keystrokes.get("DOWN")) {      /* Next command */
            assetIterator.nextCommand();
        }
        else{
            return;
        }
        updateView(assetIterator);
    }

    private void updateView(AssetIterator iter){
        receiver.updateCommands(iter);
    }

    private void printStatus() {
        System.out.println("STATUS:     " + currentState.getMode() + " | " + currentState.getType() + " | " + currentState.getInstance().getID() + " | " + currentState.getCmd().toString());
    }

    //Gets called when player turn switches. Changes the iterator on hand.
    protected void updateIterator(AssetIterator assetIterator) {
        this.assetIterator = assetIterator;
        assetIterator.first();
        /* Reboot the State object */
        currentState = new State(
                this,
                assetIterator.getCurrentMode(),
                assetIterator.getElement(), //get type
                (PlayerAsset) assetIterator.current()
        );
    }

    private void dispatchCommandForConfig(CTRLCommand thisCmd) {
        try {
            thisCmd.configure(currentState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveConfiguredCmd(CTRLCommand cmd) {
        if (cmd.isConfigured())
            receiver.handleMsg(cmd);   /* Send it to the KeyboardController */
        else System.out.println("Command wasn't configured properly");
        assetIterator.update();
    }

    /* Auxillary functions to request additional information for configuring Commands */

    public void requestTile(PlayerAsset initialHighlightAsset) {
        tileTargetter.targetTile(this, initialHighlightAsset);
    }

    public void requestAsset(String mode){
        assetTargetter.targetAsset(this, assetIterator, mode);
    }

    public void receiveTargetTile(TileAssociation cbTile) {
        currentState.setDestinationTile(cbTile);
    }
    public void receiveTargetAsset(PlayerAsset asset) {
        currentState.setDestinationAsset(asset);
    }


    private class State implements CommandComponents {
        /* Acts as an internal structure to encapsulate the MessageGenerator's state, but also as an implementation of a
         * public interface to pass around the components for configuring a Command object */
        private String currentMode;
        private String currentType;
        private PlayerAsset currentInstance;
        private CTRLCommand currentCommand;
        private MessageGenerator msgGen;
        private TileAssociation destinationTile;
        private PlayerAsset destinationAsset;

        State(MessageGenerator msgGen, String m, String t, PlayerAsset i) {
            this.msgGen = msgGen;
            this.currentMode = m;
            this.currentType = t;
            this.currentInstance = i;
//            this.currentCommand = c;
        }

        /* State-specific methods */
        protected String getMode() {
            return assetIterator.getCurrentMode();
        }

        protected String getType() {
            return assetIterator.getElement();
        }

        protected PlayerAsset getInstance() {
            return (PlayerAsset) assetIterator.current();
        }

        protected CTRLCommand getCmd() {
            return assetIterator.getCommand();
        }


        protected void setMode(String mode) {
            this.currentMode = mode;
        }

        protected void setType(String type) {
            this.currentType = type;
        }

        protected void setInstance(PlayerAsset instance) {
            this.currentInstance = instance;
        }

        protected void setCmd(CTRLCommand cmd) {
            this.currentCommand = cmd;
        }

    /* CommandComponents overrides */

        @Override   //Called by CTRLCommands once they think they're ready to be executed.
        public void requestExecution(){
            this.msgGen.receiveConfiguredCmd(this.currentCommand);
        }
        @Override
        public PlayerAsset getRequestingAsset() {return this.getInstance();}

        @Override
        //TODO: This
        public PlayerAsset getTargetAsset() {
            return destinationAsset;
        }

        @Override
        public TileAssociation getRequestingTile() {
            return null;
        }

        @Override
        public void requestDestinationTile(CTRLCommand callbackObject) {
            this.currentCommand = callbackObject;   //Set the current command so that we may call back to it
            this.destinationTile = null;            //Reset the destination tile to remove ambiguity.
            msgGen.requestTile(currentInstance);    //Tell the MessageGenerator to initiate the tile request process
            //The method below (setDestinationTile) gets called once the tile is ready.
        }

        //This gets called when the Tile request comes through
        protected void setDestinationTile(TileAssociation t){
            this.destinationTile = t;       //Update the destination tile
            try {currentCommand.callback();} catch(Exception e){e.printStackTrace();}
            //Tell the current command to query again, signaling that the tile is done.
        }

        @Override
        //Actually return the destination tile
        public TileAssociation getDestinationTile() {
            return this.destinationTile;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        public void requestDestinationStructure(CTRLCommand callbackObject){
            this.currentCommand = callbackObject;
            this.destinationAsset = null;
            msgGen.requestAsset("STRUCTURE MODE");
        }

        @Override
        public void requestDestinationRallypoint(CTRLCommand callbackObject){
            this.currentCommand = callbackObject;
            this.destinationAsset = null;
            msgGen.requestAsset("RALLY POINT MODE");
        }

        protected void setDestinationAsset(PlayerAsset asset) {
            System.out.println("In State: Got asset back: " + asset + "\nCalling back to CTRLCommand " + currentCommand.hashCode());
            this.destinationAsset = asset;       //Update the destination asset
            try {
                currentCommand.callback();
            } catch (CommandNotConfiguredException e) {
                e.printStackTrace();
            }
            //Tell the current command to query again, signaling that the tile is done.
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        //TODO: This
        public Player getOpposingPlayer() {
            return null;
        }

        @Override
        public String getUnitType() {
            UnitCreationDialog dialog = new UnitCreationDialog();
            dialog.createDialog();
            String type = dialog.returnUnitType();
            dialog.closeDialog();
            return type;
        }

        @Override
        public String getStructureType() {
            StructureCreationDialog dialog = new StructureCreationDialog();
            dialog.createDialog();
            String type = dialog.returnStructureType();
            dialog.closeDialog();
            return type;
        }

        @Override
        public String getTechTypeString() {
            TechTypeDialog dialog = new TechTypeDialog();
            dialog.createDialog();
            String type = dialog.returnTechType();
            dialog.closeDialog();
            return type;
        }

        @Override
        public String getTechAssetString() {
            TechAssetDialog dialog = new TechAssetDialog();
            dialog.createDialog();
            String type = dialog.returnTechAsset();
            dialog.closeDialog();
            return type;
        }

        @Override
        //TODO: This
        public int getNumWorkers() {
            NumWorkersDialog dialog = new NumWorkersDialog();
            dialog.createDialog();
            int type = dialog.returnWorkerCount();
            dialog.closeDialog();
            return type;
        }

        @Override
        //TODO: This
        public Unit[] getUnitList() {
            return new Unit[0];
        }
    }
}