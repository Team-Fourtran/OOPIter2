package models.playerAsset.Assets.Structures;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceStructure extends Structure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;
    int workerDensity;
    private int workRadiusSize;
    private WorkRadius workRadius;
    private HarvestStrategy harvestStrategy;
    private HashMap<String, Integer> resourceCount;
    private HashMap<String, Integer> producedCount;


    public ResourceStructure() {
    	this.workRadiusSize = 0;
        gatherers = new ArrayList<Worker>();
        producers = new ArrayList<Worker>();
        staff = new ArrayList<Worker>();
        resourceCount = new HashMap<String, Integer>();
        producedCount = new HashMap<String, Integer>();
    }
    
    public ArrayList<Worker> removeWorkersFromGathering(int num){
        ArrayList<Worker> newList = new ArrayList<>();
        int min = Math.min(num, gatherers.size());
        for (int i = 0; i < min; i++){
            newList.add(gatherers.get(0));
            gatherers.remove(0);
        }

        return newList;
    }

    public ArrayList<Worker> removeWorkersFromProduction(int num){
        ArrayList<Worker> newList = new ArrayList<>();
        int min = Math.min(num, producers.size());
        for (int i = 0; i < min; i++){
            newList.add(producers.get(0));
            producers.remove(0);
        }

        return newList;
    }

    public void addWorkersToIdle(ArrayList<Worker> worker){
    	staff.addAll(worker);
    }
    
    /*
     * set workers that are gathering resources
     */
    public void addWorkersToGathering(int assignedWorkers){
    	if (assignedWorkers >= staff.size()) {
    		for (int i = 0; i < assignedWorkers; i++) {
    			gatherers.add(staff.get(i));
    		}
    	}
    }

    /*
     * set workers that are at structure, producing
     */
    public void addWorkersToProduction(int assignedWorkers){
    	if (assignedWorkers >= staff.size()) {
    		for (int i = 0; i < assignedWorkers; i++) {
    			producers.add(staff.get(i));
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

    public void ceaseHarvesting(){
        addWorkersToIdle(removeWorkersFromGathering(gatherers.size()));
    }

    public void ceaseProduction(){
        addWorkersToIdle(removeWorkersFromProduction(producers.size()));
    }

    public void produce(String type){
        if (resourceCount.containsKey(type)){
            int resources = resourceCount.get(type);
            int amtToProduce = producers.size()*5;
            if (amtToProduce >= resources) {
                if (producedCount.containsKey(type))
                    producedCount.put(type, producedCount.get(type) + resources);
                else
                    producedCount.put(type, resources);
                resourceCount.put(type, 0);
            }
            else {
                if (producedCount.containsKey(type))
                    producedCount.put(type, producedCount.get(type) + amtToProduce);
                else
                    producedCount.put(type, amtToProduce);
                resourceCount.put(type, resourceCount.get(type) - amtToProduce);
            }
        }
    }

    public void increaseProduction(String type, double i){};

    public int getWorkerDensity(){return workerDensity;}
    public void setWorkerDensity(int w){workerDensity = w;}

    public int getNumHarvesters(){
        return gatherers.size();
    }

    public int getNumProducers(){
        return producers.size();
    }

    public void printStats(){
        System.out.println(getWorkerDensity());
        System.out.println(getWorkRadius());
    }


    public void harvestTest(int i){
        resourceCount.put("food", 1000);
    }

    public int getHarvestCount(){
        return resourceCount.get("food");
    }

    public void addWorkers(int j){
        for(int i = 0; i < j; i++){
            staff.add(new Worker());
        }
    }

    public int getProduced(){
        if (producedCount.containsKey("food"))
            return producedCount.get("food");
        else
            return 0;
    }

}
