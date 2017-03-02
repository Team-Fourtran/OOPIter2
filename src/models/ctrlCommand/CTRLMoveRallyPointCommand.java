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

    private boolean isConfigured;

    public CTRLMoveRallyPointCommand(){
        isConfigured = false;
    }

    public void configure(PlayerAsset rallyPoint, TileAssociation destination) {
        isConfigured = true;
        this.destination = destination;
        this.rallyPoint = rallyPoint;
    }

    @Override
    public CTRLMoveRallyPointCommand clone() {
        return new CTRLMoveRallyPointCommand();
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            //TODO: CHECK IF ARMY HAS A SOLDIER

            rallyPoint.accept(
                    new MovementVisitor(map, destination)
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }

    }
}
