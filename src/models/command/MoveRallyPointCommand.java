package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAssetNew.PlayerAsset;
import models.visitor.MovementVisitor;

public class MoveRallyPointCommand implements Command{
    private TileAssociation destination;
    private PlayerAsset rallyPoint; //RallyPoint
    private GameMap gameMap;

    public MoveRallyPointCommand(PlayerAsset asset, TileAssociation destination, GameMap gameMap)
    {
        this.destination = destination;
        this.rallyPoint = asset;
        this.gameMap = gameMap;
    }
    @Override
    public boolean execute() {
        //TDA
        //GameMap.generateImmediateMovement(rallyPoint, destination);
//        start.remove(rallyPoint);
//        destination.add(rallyPoint);
        rallyPoint.accept(
                new MovementVisitor(gameMap, destination)
        );
        return true;
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
