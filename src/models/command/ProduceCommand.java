package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.ResourceStructure;

/**
 * Created by Clay on 3/15/2017.
 */
public class ProduceCommand implements Command{
    private Player givingPlayer;
    private ResourceStructure producer;
    private int assignedWorkers;
    private String type;
    private boolean firstExecution;


    public ProduceCommand(GameMap map, Player player, ResourceStructure producer, int assignedWorkers, String type){
        givingPlayer = player;
        this.producer = producer;
        this.assignedWorkers = assignedWorkers;
        this.type = type;
    }

    @Override
    public void execute() {
        if (firstExecution) {
            producer.addWorkersToProduction(assignedWorkers);
            firstExecution = false;
        }

        producer.produce(type);
        if ((producer == null))
            producer.removeUniversalCommand(this);

    }

    @Override
    public double getTurns() {
        return 2;
    }
}
