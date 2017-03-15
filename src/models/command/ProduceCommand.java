package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.ResourceStructure;

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
        producer.produce(type);

    }

    @Override
    public double getTurns() {
        return 2;
    }
}
