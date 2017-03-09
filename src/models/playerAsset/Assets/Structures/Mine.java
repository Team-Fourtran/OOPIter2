package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Mine extends Structure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;

    public Mine() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
        staff = new ArrayList<>();
        gatherers = new ArrayList<>();
        producers = new ArrayList<>();

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
}
