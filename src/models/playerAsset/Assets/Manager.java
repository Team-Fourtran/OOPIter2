package models.playerAsset.Assets;

import models.command.Command;

public interface Manager {

    //public void addCommand(Command c);
    //public void decommision();
    public void freeID(String assetID);
}