package models.playerAsset;

import models.command.Command;
import models.visitor.AssetVisitor;

import java.util.LinkedList;
import java.util.Queue;

/* Class that represents all of the common features of a what the player can own
   This includes Army, Structure, and Unit
*/
public abstract class PlayerAsset {

    private CommandArray universalQueue = new CommandArray();
    private double movementTurns = 0.33; //Should be overridden by subtypes

    protected int offDamage;
    protected int defDamage;
    protected int armor;
    protected int maxHealth;
    protected int currentHealth;
    protected int upkeep;
    protected boolean poweredUp;
    protected String assetID;
    protected boolean hasExecutedCommand = false;
    protected Queue<Command> commandQueue = new LinkedList<>();
    protected int commandCount = 0;
    protected int moveCounter = 0;

    //Various getter and setters for attributes
    public void setID(String id){
        assetID = id;
    }
    public String getID(){
        return assetID;
    }
    public int getOffDamage(){
        return offDamage;
    }
    public int getDefDamage(){
        return defDamage;
    }
    public int getArmor(){
        return armor;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
    public boolean getPoweredUp(){
        return poweredUp;
    }
    public int getUpkeep(){
        return upkeep;
    }
    public double getMovementTurns(){
        return movementTurns;
    }

    //Power up a unit, increase the resource consumption back to %100
    public void powerUp(){
        if (!poweredUp)
            upkeep *= 4;
    }

    //Power down a powered up unit and change its resource consumption to %25
    public void powerDown(){
        if (poweredUp)
            upkeep /= 4;
    }

    //Add a command to its queue
    //if no command has been executed this turn, execute it
    public void addCommand(Command c){
        commandQueue.add(c);
        if (!hasExecutedCommand) {
            executeCommand();
        }
    }

    public void addUniversalCommand(Command c){
        universalQueue.add(c);
    }

    public void removeUniversalCommand(Command c) {
        universalQueue.remove(c);
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
                if (equal(commandQueue.peek().getTurns(), commandCount)) {
                    commandQueue.remove().execute();
                    commandCount = 0;
                    hasExecutedCommand = true;
                }
            } else {
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
                    commandQueue.remove().execute();
                    moveCounter++;
                }

                if (moveCounter == 3 || endMovement)
                    hasExecutedCommand = true;

            }
            if(hasExecutedCommand){
                moveCounter = 0;
            }
        }

    }

    //helper method for execute to compare equality for double and int
    public boolean equal(double d, int i){
        double n = d-i;
        if (n < 0.000001)
            return true;
        return false;
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

    //get asset type, overridden in subclasses
    public String getType(){
        return "basic asset type";
    }

    public abstract void accept(AssetVisitor v);

}
