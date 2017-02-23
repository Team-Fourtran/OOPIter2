package models.playerAssetNew;

import models.command.Command;
import models.visitor.AssetVisitor;

import java.util.LinkedList;
import java.util.Queue;

/* Class that represents all of the common features of a what the player can own
   This includes Army, Structure, and Unit
*/
public abstract class PlayerAsset {

    private String ID;
    private int offDamage;
    private int defDamage;
    private int armor;
    private int maxHealth;
    private int currentHealth;
    private int upkeep;
    private boolean poweredUp;
    private boolean hasExecutedCommand = false;
    protected Queue<Command> commandQueue = new LinkedList<>();
    int commandCount = 0;
    private  int moveCounter = 0;

    //Various getter and setters for attributes
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

    public abstract void accept(AssetVisitor v);

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

    //check if queue is empty or not
    public boolean emptyQueue() {
        if (commandQueue.size() == 0)
            return true;
        return false;
    }

    public boolean equal(double d, int i){
        double n = d-i;
        if (n < 0.000001)
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

    public void setID(String ID) {
        this.ID = ID;
    }
}
