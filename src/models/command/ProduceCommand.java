package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.*;

public class ProduceCommand implements Command{
    private Player givingPlayer;
    private ResourceStructure producer;
    private int assignedWorkers;
    private boolean firstExecution;


    public ProduceCommand(GameMap map, Player player, ResourceStructure producer, int assignedWorkers){
        givingPlayer = player;
        this.producer = producer;
        this.assignedWorkers = assignedWorkers;
        firstExecution = true;
    }

    @Override
    public void execute() {
        if (assignedWorkers == 0){
            producer.ceaseProduction();
            producer.removeUniversalCommand(this);
            System.out.println("gone1");
            return;
        }
        else{
            if (firstExecution) {
                producer.addWorkersToProduction(assignedWorkers);
                firstExecution = false;
            }
        }

        System.out.println(producer.getNumProducers());
        if ((producer == null || producer.getNumProducers() == 0)) {
            producer.removeUniversalCommand(this);
            System.out.println("gone2");
            return;
        }
        if(producer instanceof Capital){
            producer.produce("food");
        }
        else if (producer instanceof Farm){
            producer.produce("food");
        }
        else if (producer instanceof Mine){
            producer.produce("ore");
        }
        else if (producer instanceof PowerPlant){
            producer.produce("energy");
        }
        else{
            producer.removeUniversalCommand(this);
        }

    }

    @Override
    public double getTurns() {
        return 2;
    }
}
