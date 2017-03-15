package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.ProduceCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

public class CTRLProduceCommand implements CTRLCommand {
    private ResourceStructure producer;
    private int assignedWorkers;
    private String type;
    private boolean isConfigured;


    public CTRLProduceCommand(){isConfigured = false;}

    public void configure(ResourceStructure producer,int assignedWorkers, String type) {
        isConfigured = true;
        this.producer = (ResourceStructure) producer;
        this.assignedWorkers = assignedWorkers;
        this.type = type;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {

    }

    @Override
        public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
            if(isConfigured){
                producer.addUniversalCommand(
                        new ProduceCommand(
                                map,
                                player,
                                producer,
                                assignedWorkers,
                                type
                        )
                );
            }
        }

    @Override
    public boolean isConfigured() {
        return isConfigured;
    }
}
