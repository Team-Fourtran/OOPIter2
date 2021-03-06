//package controllers;
//
//import models.assetOwnership.TileAssociation;
//import models.ctrlCommand.CTRLCommand;
//import models.playerAsset.Assets.Units.Unit;
//import models.playerAsset.Iterators.AssetIterator;
//import models.playerAsset.Iterators.CommandIterator;
//import models.playerAsset.Assets.*;
//import models.visitor.CommandListVisitor;
//
//import java.util.HashMap;
//
//class MessageGenerator implements KeyPressListener{
//
//    private AssetIterator assetIterator;  //Used to iterate through current Player's assets
//    private KeyboardController receiver;
//    private TileTargetting tileTargetter;
//
//    State currentState;
//
//    MessageGenerator(KeyboardController receiver, KeyPressInformer keyInformer, AssetIterator assIter, TileTargetting tt){
//        this.receiver = receiver;                   //Set up who will receive Commands once they're generated
//
//        keyInformer.registerClient(this);    //Register self to get keypress notifications from the keyInformer
//
//        tileTargetter = tt;                         //Used for querying the game map for a target tile
//        this.assetIterator = assIter;               //Set up the asset iterator
//        assetIterator.first();
//
//        CommandListVisitor cmdGetter = new CommandListVisitor();  //Set up the CommandList visitor
//
//        PlayerAsset tempAsset = (PlayerAsset) assetIterator.getElement();
//        tempAsset.accept(cmdGetter);
//        CommandIterator tempIter = cmdGetter.getIterator();
//        /* Initialize the State object */
//        currentState = new State(
//                this,
//                assetIterator.getCurrentMode(),
//                assetIterator.getCurrentType(),
//                tempAsset,
//                tempIter,
//                tempIter.first()
//        );
//    }
//
//    private void generateMessage(){
//        String theMessage = "Mode: " + currentState.getMode() + "\tType: " + currentState.getType();
//        theMessage += "\tAsset: " + currentState.getInstance() + "\tCommand: " + currentState.getCmd() + "\n";
//
//        System.out.println(theMessage);
//
//        //Decide whether the current command needs a TileAssociation to be configured
//
//        //Testing:
//        boolean needsTile = true;
//
//        //If the MainScreen needs to be queried for a tile
//        if(needsTile){
//            tileTargetter.targetTile(this, currentState.getInstance());
//        }
//
//        //If we can proceed without it
//        else {
//            finishGeneratingMessage(null);
//        }
//    }
//
//    private void finishGeneratingMessage(TileAssociation targetTile){
//        //Sends receiver a message as generated by the current Mode
//        if( null != targetTile)
//            System.out.println("Target tile: " + targetTile);
//
//        CTRLCommand thisCmd = currentState.getCmd();
//        boolean sendCmd = true;
//        try {
//            thisCmd.configure(currentState);
//        } catch(Exception e){
//            e.printStackTrace();
//            sendCmd = false;
//        }
//        /* If configuring the command didn't throw an error */
//        if(sendCmd)
//            receiver.handleMsg("hi");   /* Send it to the KeyboardController */
//    }
//
//    public void receiveTargetTile(TileAssociation cbTile){
//        finishGeneratingMessage(cbTile);
//    }
//
//    @Override //Listen to notifications from a KeyPressInformer
//    public void updateKeysPressed(HashMap<String, Boolean> kp) {
//        interpretKeystrokes(kp);
//    }
//
//    /* Updates the State object based on the keystrokes detected */
//    private void interpretKeystrokes(HashMap<String, Boolean> keystrokes){
//        if(keystrokes.get("ENTER")){
//            generateMessage();
//        }
//
//        /* Keypress combinations with CONTROL+[some key] cycle MODE or TYPE */
//        if(keystrokes.get("CONTROL")){
//
//            /* CONTROL+{UP/DOWN}: Cycle MODE */
//            if(keystrokes.get("UP")){
//                assetIterator.prevMode();           //CONTROL+UP: Previous Mode
//            } else if(keystrokes.get("DOWN")){
//                assetIterator.nextMode();           //CONTROL+DOWN: Next Mode
//            }
//
//            /* CONTROL+{LEFT/RIGHT}: Cycle TYPE */
//            else if(keystrokes.get("LEFT")){        //CONTROL+LEFT: Previous Type
//                assetIterator.prevType();
//            } else if(keystrokes.get("RIGHT")){     //CONTROL+RIGHT: Next Type
//                assetIterator.nextType();
//            }
//        }
//
//        /* Keypresses without control cycle TYPE INSTANCES and COMMANDS */
//        else {
//
//            //LEFT/RIGHT: Cycle Type Instances
//            if(keystrokes.get("LEFT")){
//                assetIterator.prev();
//
//            } else if(keystrokes.get("RIGHT")){
//                assetIterator.next();
//            }
//
//            /* UP/DOWN: Cycle Commands */
//            else if(keystrokes.get("UP")){               /* Previous command */
//                currentState.getCmdIter().prev();
//            } else if(keystrokes.get("DOWN")){      /* Next command */
//                currentState.getCmdIter().next();
//            }
//        }
//
//        /* Update the State based on the new Iterator info */
//        currentState.setMode(assetIterator.getCurrentMode());
//        currentState.setType(assetIterator.getCurrentType());
//        currentState.setInstance((PlayerAsset)assetIterator.getElement());
//
//        /* Update the CommandIterator */
//        CommandListVisitor cmdGetter = new CommandListVisitor();
//        ((PlayerAsset) assetIterator.getElement()).accept(cmdGetter);
//        CommandIterator tempIter = cmdGetter.getIterator();
//        currentState.setCmdIter(tempIter);
//
//        /* Update the command */
//        currentState.setCmd(tempIter.current());
//
//    }
//
//    //Gets called when player turn switches. Changes the iterator on hand.
//    protected void updateIterator(AssetIterator assetIterator){
//        this.assetIterator = assetIterator;
//    }
//}
//
//class State implements CommandComponents{
//    /* Acts as an internal structure to encapsulate the MessageGenerator's state, but also as an implementation of a
//     * public interface to pass around the components for configureing a Command object */
//    private String currentMode;
//    private String currentType;
//    private PlayerAsset currentInstance;
//    private CommandIterator currentCmdIter;
//    private CTRLCommand currentCommand;
//    private MessageGenerator msgGen;
//
//    State(MessageGenerator msgGen, String m, String t, PlayerAsset i, CommandIterator ci, CTRLCommand c){
//        this.msgGen = msgGen;
//        this.currentMode = m;
//        this.currentType = t;
//        this.currentInstance = i;
//        this.currentCmdIter = ci;
//        this.currentCommand = c;
//    }
//
//    protected String getMode(){
//        return this.currentMode;
//    }
//    protected String getType(){
//        return this.currentType;
//    }
//    public PlayerAsset getInstance(){
//        return this.currentInstance;
//    }
//    protected CommandIterator getCmdIter(){
//        return this.currentCmdIter;
//    }
//    public CTRLCommand getCmd(){
//        return this.currentCommand;
//    }
//
//
//    protected void setMode(String mode){
//        this.currentMode = mode;
//    }
//    protected void setType(String type){
//        this.currentType = type;
//    }
//    protected void setInstance(PlayerAsset instance){
//        this.currentInstance = instance;
//    }
//    protected void setCmdIter(CommandIterator iter){
//        this.currentCmdIter = iter;
//    }
//    protected void setCmd(CTRLCommand cmd){
//        this.currentCommand = cmd;
//    }
//
//    @Override
//    public PlayerAsset getRequestingAsset() {
//        return currentInstance;
//    }
//
//    @Override
//    public PlayerAsset getTargetAsset() {
//        return null;
//    }
//
//    @Override
//    public TileAssociation getRequestingTile() {
//        return null;
//    }
//
//    @Override
//    public TileAssociation getDestinationTile() {
//        return msgGen.;
//    }
//
//    @Override
//    public Player getOpposingPlayer() {
//        return null;
//    }
//
//    @Override
//    public String getString() {
//        return null;
//    }
//
//    @Override
//    public int getInt() {
//        return 0;
//    }
//
//    @Override
//    public Unit[] getUnitList() {
//        return new Unit[0];
//    }
//}