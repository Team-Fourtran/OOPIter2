package src.models.playerAsset;

import java.util.ArrayList;

public abstract class Manager{

    protected ArrayList<PlayerAsset> assetList;

    //go through the structures and, if possible, execute a command
    //used at beginning of player's turn
    public void executeCommands(){
        for (PlayerAsset a: assetList) {
            if (!a.emptyQueue()) {
                a.executeCommand();
            }
        }
    }

    public void resetCommands(){
        for (PlayerAsset a: assetList)
            a.resetCommands();
    }


    public int calculateTotalUpkeep(){
        int totalUpkeep = 0;
        for (PlayerAsset a: assetList){
            totalUpkeep += a.getUpkeep();
        }
        return totalUpkeep;
    }

    public abstract void freeID(String assetID);
    public abstract void makeIterator(String assetID);
}