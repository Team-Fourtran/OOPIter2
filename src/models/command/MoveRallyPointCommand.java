/*
IMMEDIATE COMMAND FROM CONTROLLER
 */

package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.PlayerAsset;
import models.visitor.MovementVisitor;

public class MoveRallyPointCommand implements Command{
    private TileAssociation destination;
    private PlayerAsset rallyPoint; //RallyPoint
    private GameMap gameMap;

    public MoveRallyPointCommand(PlayerAsset rallyPoint, TileAssociation destination, GameMap gameMap)
    {
        this.destination = destination;
        this.rallyPoint = rallyPoint;
        this.gameMap = gameMap;
    }
    @Override
    public void execute() {
        //TDA
        //GameMap.generateImmediateMovement(rallyPoint, destination);
//        start.remove(rallyPoint);
//        destination.add(rallyPoint);
        rallyPoint.accept(
                new MovementVisitor(gameMap, destination)
        );
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
