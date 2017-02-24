package models.command;


import models.assetOwnership.TileAssociation;
import models.playerAssetNew.PlayerAsset;
import models.visitor.AssetVisitor;
import models.visitor.MovementVisitor;

public interface Command {
    boolean execute();
    double getTurns();
}

