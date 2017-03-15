package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLPickUpWorkers implements CTRLCommand{
    private CommandComponents parts;
    private Structure destination;
    private RallyPoint rallyPoint;
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
    //TODO: Make this work using the CommandComponents mechanaism
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {

    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        //Unused...For now?
    }


    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException {

    }

    @Override
    public boolean isConfigured() {
        return false;
    }
}
