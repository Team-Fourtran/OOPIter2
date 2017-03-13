package controllers;

import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.Units.Unit;

public interface CommandComponents {
    PlayerAsset getRequestingAsset();
    PlayerAsset getTargetAsset();
    TileAssociation getRequestingTile();
    TileAssociation getDestinationTile();
    Player getOpposingPlayer();
    String getString();
    int getInt();
    Unit[] getUnitList();
}
