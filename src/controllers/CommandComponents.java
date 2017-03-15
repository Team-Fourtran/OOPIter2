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
    void requestDestinationStructure(CTRLCommand callbackObj);
    void requestDestinationRallypoint(CTRLCommand callbackObj);
    TileAssociation getDestinationTile();
    String getTechTypeString();
    String getTechAssetString();
    int getNumWorkers();
    Player getOpposingPlayer();
    String getStructureType();
    String getUnitType();
    Unit[] getUnitList();
    void requestExecution();    //Called by CTRLCommands once they think they're ready to be executed

}
