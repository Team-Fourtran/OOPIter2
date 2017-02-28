/*
IMMEDIATE COMMAND FROM CONTROLLER
 */

package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.visitor.MovementVisitor;

public class CTRLMoveRallyPointCommand implements CTRLCommand{
    private TileAssociation destination;
    private PlayerAsset rallyPoint; //RallyPoint

    public CTRLMoveRallyPointCommand(PlayerAsset rallyPoint, TileAssociation destination)
    {
        this.destination = destination;
        this.rallyPoint = rallyPoint;
    }
    @Override
    public void execute(GameMap map, Player player) {

        //TODO: CHECK IF ARMY HAS A SOLDIER

        rallyPoint.accept(
                new MovementVisitor(map, destination)
        );
    }
}
