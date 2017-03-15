package models.playerAsset.Assets;

import models.assetOwnership.TileObserver;
import models.command.Command;
import models.visitor.AssetVisitor;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class PlayerAsset extends Observable {
    protected String assetID;
    private double movementTurns = 0.33; //Should be overridden by subtypes
    private CommandArray universalQueue = new CommandArray();
    private boolean hasExecutedCommand = false;
    private Queue<Command> commandQueue = new LinkedList<>();
    private int commandCount = 0;
    private int moveCounter = 0;
    private CopyOnWriteArrayList<AssetObserver> observers = new CopyOnWriteArrayList<AssetObserver>();
    
    

    public void setID(String id){
        assetID = id;
    }
    public String getID(){
        return assetID;
    }

    public double getMovementTurns(){
        return movementTurns;
    }
    public void setMovementTurns(double moves) {movementTurns = moves;}

    public String getType(){
        return "basic asset type";
    }
    
    public abstract void accept(AssetVisitor v);

    //Add a command to its queue
    //if no command has been executed this turn, execute it
    public void addCommand(Command c){
        commandQueue.add(c);
        if (!hasExecutedCommand) {
            executeCommand();
        }
    }

    //execute the first command in the queue
    //if turns are divisible by 1, then execute or wait until enough turns have passed
    //if it's a movement command, make sure the max amount of moves can be made
    public void executeCommand() {
        universalQueue.execute();

        if (!hasExecutedCommand && !commandQueue.isEmpty()) {

            int turns = (int) commandQueue.peek().getTurns();

            if (turns != 0) {
                commandCount++;
                if (equal(turns, commandCount)) {
                    commandQueue.remove().execute();
                    commandCount = 0;
                }
                hasExecutedCommand = true;
            }
            else {
                int numCommands = 0;
                double turnCount = 0;
                boolean endMovement = false;
                for (Command c : commandQueue) {
                    turnCount += c.getTurns();
                    if (turnCount >= 1) {
                        endMovement = true;
                        break;
                    }
                    numCommands++;
                }

                for (int i = 0; i < numCommands; i++) {
                    if(!commandQueue.isEmpty()){
                        commandQueue.remove().execute();
                        moveCounter++;
                    }
                }

                if (moveCounter == 3 || endMovement)
                    hasExecutedCommand = true;

            }
            if(hasExecutedCommand){
                moveCounter = 0;
            }
        }

    }

    public void addUniversalCommand(Command c){
        universalQueue.add(c);
    }

    public void removeUniversalCommand(Command c) {
        universalQueue.remove(c);
    }



    //check if queue is empty or not
    public boolean emptyQueue() {
        if (commandQueue.size() == 0)
            return true;
        return false;
    }

    //clear all entries in the queue
    public void clearQueue(){
        commandQueue.clear();
    }


    //reset the asset's ability to execute a command
    public void resetCommands(){
        hasExecutedCommand = false;
    }

    //helper method for execute to compare equality for double and int
    public boolean equal(double d, int i){
        double n = d-i;
        if (n < 0.000001)
            return true;
        return false;
    }

    /*
     * Notifies observers that it has left/died
     */
    public void notifyLeave() {
        for(AssetObserver ob : observers){
            ob.updateLeave(this);
        }
    }
    
    public void addObserver(AssetObserver o) {
    	observers.add(o);
    }
    
    public void removeObserver(AssetObserver o) {
    	observers.remove(o);
    }
}
