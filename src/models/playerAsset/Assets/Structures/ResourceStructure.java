package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class ResourceStructure extends Structure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;
    int workerDensity;
    int workRadius;

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

    public int getWorkerDensity(){return workerDensity;}
    public int getWorkRadius(){return workRadius;}
    public void setWorkerDensity(int w){workerDensity = w;}
    public void setWorkRadius(int r){workRadius = r;}

}
