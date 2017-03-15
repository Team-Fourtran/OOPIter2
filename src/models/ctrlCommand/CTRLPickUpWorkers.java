package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLPickUpWorkers implements CTRLCommand{
    private CommandComponents parts;
    private Structure destination;
    private PlayerAsset rallyPoint;
    private boolean isConfigured;

    public CTRLPickUpWorkers(){
        isConfigured = false;
    }

    public void configure(RallyPoint rallyPoint, Structure structure){
        this.destination = structure;
        this.rallyPoint = rallyPoint;
        isConfigured = true;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        System.out.println(parts + "\n" + destination);
        this.destination = (Structure) parts.getTargetAsset(); //Query parts for the destination asset.
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
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.rallyPoint = parts.getRequestingAsset();
        parts.requestDestinationStructure(this);
        isConfigured = false;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
        System.out.println(rallyPoint.toString() + "\n" + destination.toString());
    }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }
}
