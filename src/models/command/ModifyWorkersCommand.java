package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class ModifyWorkersCommand implements Command{
    private GameMap map;
    private Army army;
    private Structure structure;
    private boolean pickingUp;
    private int numWorkers;

    public ModifyWorkersCommand(boolean pickingUp, GameMap map, Army army, Structure structure, int numWorkers){
        this.map = map;
        this.army = army;
        this.structure = structure;
        this.pickingUp = pickingUp;
        this.numWorkers = numWorkers;
    }

    @Override
    public void execute() {
        if(map.calculateDistance(army, structure) == 0){
            if(pickingUp){
                ArrayList<Worker> workers = structure.acquireWorkers(numWorkers);
                if (workers != null){
                    for(Worker w : workers){
                        army.addWorker(w);
                    }
                }
            }
            else{
                structure.assignWorkers(army.removeWorkers());
            }
        }
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
