/*
IMMEDIATE COMMAND FROM CONTROLLER
 */

package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;
import models.visitor.MovementVisitor;

public class CTRLMoveRallyPointCommand implements Command{
    private TileAssociation destination;
    private PlayerAsset rallyPoint; //RallyPoint
    private GameMap gameMap;

    public CTRLMoveRallyPointCommand(PlayerAsset rallyPoint, TileAssociation destination, GameMap gameMap)
    {
        this.destination = destination;
        this.rallyPoint = rallyPoint;
        this.gameMap = gameMap;
    }
    @Override
    public void execute() {

        //TODO: CHECK IF ARMY HAS A SOLDIER

        rallyPoint.accept(
                new MovementVisitor(gameMap, destination)
        );
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
