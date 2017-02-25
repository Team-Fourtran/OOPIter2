package models.playerAsset;

import models.command.Command;

public interface Manager{

    //public void addCommand(Command c);
    //public void decommision();
    public int calculateTotalUpkeep();
    public void freeID(String assetID);
}