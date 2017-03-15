package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.command.ProduceCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.ResourceStructure;

public class CTRLProduceCommand implements CTRLCommand {
    private ResourceStructure producer;
    private int assignedWorkers;
    private boolean isConfigured;


    public CTRLProduceCommand(){isConfigured = false;}

    public void configure(ResourceStructure producer,int assignedWorkers, String type) {
        isConfigured = true;
        this.producer = (ResourceStructure) producer;
        this.assignedWorkers = assignedWorkers;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.producer = (ResourceStructure) parts.getRequestingAsset();
        this.assignedWorkers = parts.getNumWorkers();
        isConfigured = true;
        parts.requestExecution();
    }

    @Override
        public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
            if(isConfigured){
                producer.addUniversalCommand(
                        new ProduceCommand(
                                map,
                                player,
                                producer,
                                assignedWorkers
                        )
                );
            }
        }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }
}
