package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Structures.Structure;
import models.assetOwnership.Radius;
import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceStructure extends Structure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;
    int workerDensity;
    ArrayList<Worker> idlers;
    private int workRadiusSize;
    private WorkRadius workRadius;
    private HarvestStrategy harvestStrategy;
    private HashMap<String, Integer> resourceCount;


    public ResourceStructure() {
    	this.workRadiusSize = 0;
        gatherers = new ArrayList<Worker>();
        producers = new ArrayList<Worker>();
        idlers = new ArrayList<Worker>();
        resourceCount = new HashMap<String, Integer>();
    }
    
    public ArrayList<Worker> removeWorkersFromGathering(int num){
        ArrayList<Worker> newList = new ArrayList<>();
        for (int i = 0; i < num && i < gatherers.size(); i++){
            newList.add(gatherers.get(i));
            gatherers.remove(i);
        }

        return newList;
    }

    public ArrayList<Worker> removeWorkersFromProduction(int num){
        ArrayList<Worker> newList = new ArrayList<>();
        for (int i = 0; i < num && i < producers.size(); i++){
            newList.add(producers.get(i));
            producers.remove(i);
        }

        return newList;
    }

    public void addWorkersToIdle(ArrayList<Worker> worker){
    	idlers.addAll(worker);
    }
    
    /*
     * set workers that are gathering resources
     */
    public void addWorkersToGathering(int assignedWorkers){
    	if (assignedWorkers >= idlers.size()) {
    		for (int i = 0; i < assignedWorkers; i++) {
    			gatherers.add(idlers.get(i));
    		}
    	}
    }

    /*
     * set workers that are at structure, producing
     */
    public void addWorkersToProduction(int assignedWorkers){
    	if (assignedWorkers >= idlers.size()) {
    		for (int i = 0; i < assignedWorkers; i++) {
    			producers.add(idlers.get(i));
    		}
    	}
    }
    
    // structure will harvest from given tile. returns false if tile is depleted of resource
    public int harvest(TileAssociation target) {
    	int totalCount = 0;
    	// for each worker, harvest
    	for (int i = 0; i < gatherers.size(); i++) {
    		totalCount += harvestStrategy.harvest(target);
    	}
		return totalCount;
    }
    
    public int getWorkRadiusSize() {
    	return workRadiusSize;
    }
    
    public void setRadiusSize(int r) {
    	this.workRadiusSize = r;
    	workRadius.setRadiusSize(workRadiusSize);
    	workRadius.updateInfluencedTiles();
    }
    
    // can change the harvest strategy, important for Capital
    public void setHarvestType(HarvestStrategy s) {
    	this.harvestStrategy = s;
    }
    
    public void setWorkRadius(WorkRadius workRadius) {
    	this.workRadius = workRadius;
    }
    
    public WorkRadius getWorkRadius() {
    	return workRadius;
    }
    
    public void addResourceCount(String type, int count) {
    	if (resourceCount.containsKey(type)) {
    		resourceCount.put(type, resourceCount.get(type)+count);
    	} else {
    		resourceCount.put(type, count);
    	}
    }

    public void increaseProduction(String type, double i){};

    public int getWorkerDensity(){return workerDensity;}
    public void setWorkerDensity(int w){workerDensity = w;}

    public void printStats(){
        System.out.println(getWorkerDensity());
        System.out.println(getWorkRadius());
    }

}
