package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Structures.Structure;
import models.assetOwnership.Radius;
import models.assetOwnership.WorkRadius;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

/**
 * Created by Clay on 3/8/2017.
 */
public class ResourceStructure extends Structure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;
    private int workRadiusSize;
    private WorkRadius workRadius;
    private HarvestStrategy harvestStrategy;
    // resource type

    public ResourceStructure() {
    	this.workRadiusSize = 0;
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

    public void addWorkersToGathering(ArrayList<Worker> list){
        gatherers.addAll(list);
    }

    public void addWorkersToProduction(ArrayList<Worker> list){
        producers.addAll(list);
    }
    
    public void startHarvest() {
    	System.out.println("T to harvest: " + harvestStrategy.harvest());
    	// TODO: send workers??? put workers as a parameter in this function
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
}
