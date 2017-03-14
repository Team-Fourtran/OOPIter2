package controllers;

import models.assetOwnership.TileAssociation;
import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.Units.Unit;

public interface CommandComponents {
    PlayerAsset getRequestingAsset();
    PlayerAsset getTargetAsset();
    TileAssociation getRequestingTile();

    void requestDestinationTile(CTRLCommand callbackObj);   //Initiates a request to update the destination tile using the View's mechanism
    TileAssociation getDestinationTile();   //Actually gets the tile

    Player getOpposingPlayer();
    String getString();
    int getInt();
    Unit[] getUnitList();

    void requestExecution();    //Called by CTRLCommands once they think they're ready to be executed
}
