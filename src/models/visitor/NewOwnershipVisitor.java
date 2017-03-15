package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.tileInfo.*;

public class NewOwnershipVisitor implements TileVisitor{
    private GameMap map;
    private Player player;
    private PlayerAsset asset;
    private TileAssociation tile;

    public NewOwnershipVisitor(GameMap map, Player player, TileAssociation tile, PlayerAsset asset){
        this.player = player;
        this.map = map;
        this.asset = asset;
        this.tile = tile;
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
    public void visitLandMine(LandMine landMine) {
        asset.accept(
                new DeathVisitor(map, player)
        );
        tile.removeItem();
    }

    @Override
    public void visitObstacleItem(ObstacleItem item) {

    }

    @Override
    public void visitResourcePackage(ResourcePackage resourcePackage) {

    }
}
