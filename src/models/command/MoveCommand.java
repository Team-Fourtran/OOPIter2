package models.command;

import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;

public class MoveCommand implements Command{
    private TileAssociation start, end;
    private PlayerAsset asset;

    public MoveCommand(PlayerAsset asset, TileAssociation start, TileAssociation end)
    {
        this.start = start;
        this.end = end;
        this.asset = asset;
    }
    @Override
    public void execute() {
        start.remove(asset);
        end.add(asset);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getTurns() {
        /*
        return asset.getMovementTurns();
            --Will return representation of how far the asset can move
            --If army, it will take the largest of the BG's getTurns();
         */
        return asset.getMovementTurns();
    }
}
