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
    void requestDestinationTile(CTRLCommand callbackObj);
    TileAssociation getDestinationTile();
    Player getOpposingPlayer();
    String getStructureType();
    String getUnitType();
    int getInt();
    Unit[] getUnitList();
    void requestExecution();
}
