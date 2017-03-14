/*
IMMEDIATE COMMAND FROM CONTROLLER
 */

package models.ctrlCommand;

import controllers.CommandComponents;
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

    public void configure(PlayerAsset rallyPoint, TileAssociation destination){
        this.rallyPoint = rallyPoint;
        this.destination = destination;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.rallyPoint = parts.getRequestingAsset();
        this.destination = parts.getDestinationTile();
        isConfigured = true;
        //TODO: This
    }


    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            //TODO: CHECK IF ARMY HAS A SOLDIER
            rallyPoint.accept(
                    new MovementVisitor(map, player, destination)
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Move Rally Point";
    }
}
