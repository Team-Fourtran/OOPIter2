package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.command.BuildCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;

public class CTRLBuildCommand implements CTRLCommand{
    private RallyPoint rallyPoint;
    private String structureType;
    private int numWorkersAssigned;
    private boolean isConfigured;
    private CommandComponents parts;

    public CTRLBuildCommand(){
        isConfigured = false;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.rallyPoint = (RallyPoint) parts.getRequestingAsset();

        //Needs to request a structure type and a number from the CommandComponents part object
        this.structureType = structureType;
        this.numWorkersAssigned = numWorkersAssigned;
    }

    @Override
    //TODO: Figure out how to do multiple callbacks
    public void queryAgain() throws CommandNotConfiguredException {

        isConfigured = true;
    }
    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
        if(isConfigured){
            rallyPoint.addCommand(
                    new BuildCommand(
                            map,
                            player,
                            rallyPoint,
                            structureType,
                            numWorkersAssigned
                    )
            );
        } else{
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Build";
    }
}
