package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class PickupWorkersCommand implements Command{
    private GameMap map;
    private Army army;
    private Structure structure;
    private int numWorkers;

    public PickupWorkersCommand(GameMap map, Army army, Structure structure, int numWorkers){
        this.map = map;
        this.army = army;
        this.structure = structure;
        this.numWorkers = numWorkers;
    }

    @Override
    public void execute() {
        if(map.calculateDistance(army, structure) == 0){
            //Good to pickup
            ArrayList<Worker> workers = structure.acquireWorkers(numWorkers);
            if (workers != null){
                for(Worker w : workers){
                    army.addWorker(w);
                }
            }
        }
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
