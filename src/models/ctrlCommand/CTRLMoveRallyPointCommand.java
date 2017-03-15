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
    private CommandComponents parts;
    private TileAssociation destination;
    private PlayerAsset rallyPoint; //RallyPoint

    private boolean isConfigured;

    public CTRLMoveRallyPointCommand(){
        isConfigured = false;
    }

    public void configure(PlayerAsset rallyPoint, TileAssociation destination) {
        this.destination = destination;
        this.rallyPoint = rallyPoint;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.rallyPoint = parts.getRequestingAsset();
        parts.requestDestinationTile(this);
        isConfigured = false;   //Still not configured
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        System.out.println(parts + "\n" + destination);
        this.destination = parts.getDestinationTile(); //Query parts for the destination tile.
        System.out.println(parts + "\n" + destination);
        if(null != destination){       //Calling requestDestinationTile set it to null before initiating the highlighting
            //If it's not null, highlighting worked properly and we have a DestinationTile
            isConfigured = true;    //Flip the flag so that it'll execute properly without exceptions
            parts.requestExecution();   //Request execution
        } else {
            throw new CommandNotConfiguredException("queryAgain() was called, but the DestinationTile is null");
        }
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
