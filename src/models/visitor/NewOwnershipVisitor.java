package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.tileInfo.*;

public class NewOwnershipVisitor implements TileVisitor{
    private PlayerAsset asset;

    public NewOwnershipVisitor(PlayerAsset asset){
        this.asset = asset;
    }

    @Override
    public void visitNormal(Normal tile) {

    }

    @Override
    public void visitSlowing(Slowing tile) {

    }

    @Override
    public void visitImpassable(Impassable tile) {

    }

    @Override
    public void visitLandMine(LandMine item) {

    }

    @Override
    public void visitObstacleItem(ObstacleItem item) {

    }
}
