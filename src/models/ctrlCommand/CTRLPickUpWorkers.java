package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.visitor.MovementVisitor;

public class CTRLPickUpWorkers implements CTRLCommand{
    private CommandComponents parts;
    private Structure destination;
    private PlayerAsset rallyPoint;
    private int numWorkers;
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
        this.destination = (Structure) parts.getTargetAsset();
        System.out.println(parts + "\n" + destination);
        if(null != destination){
            isConfigured = true;
            parts.requestExecution();   //Request execution
        } else {
            throw new CommandNotConfiguredException("queryAgain() was called, but the DestinationTile is null");
        }
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.rallyPoint = parts.getRequestingAsset();
        this.numWorkers = parts.getInt();
        parts.requestDestinationStructure(this);
        isConfigured = false;
    }


    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
        System.out.println(rallyPoint.toString() + "\n" + destination.toString());
        MovementVisitor vis = new MovementVisitor(map, player, map.getLocation(destination));
        vis.setNeedWorkers(destination, numWorkers);
        if(isConfigured){
            rallyPoint.accept(
                    vis
            );
        }
        else{
            throw new CommandNotConfiguredException("Shit...");
        }
    }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }

    @Override
    public String toString(){
        return "Pick Up Workers";
    }
}
