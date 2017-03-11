package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.visitor.NewOwnershipVisitor;

public class MoveCommand implements Command{
    private TileAssociation start, end;
    private PlayerAsset asset;
    private Player player;
    private GameMap map;

    public MoveCommand(GameMap map, Player player, PlayerAsset asset, TileAssociation start, TileAssociation end)
    {
        this.map = map;
        this.start = start;
        this.end = end;
        this.asset = asset;
        this.player = player;
    }
    @Override
    public void execute() {
        //If the asset has been removed due to death, we can't move the asset,
        //but this still has to execute due to the visitor.
        if (!start.remove(asset)){
            return;
        }
        end.add(asset);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end.accept(
                new NewOwnershipVisitor(map, player, end, asset)
        );
    }

    @Override
    public double getTurns() {
        /*
        return asset.getMovementTurns();
            --Will return representation of how far the asset can move
            --If army, it will take the largest of the BG's getTurns();
         */
        return asset.getMovementTurns() * end.getMovementCost();
    }
}
