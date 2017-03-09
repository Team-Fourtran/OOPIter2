package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.ResourceStructure;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Capital extends Structure implements ResourceStructure{

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;

    public Capital()
    {
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
        setRange(3);
        staff = new ArrayList<>();
        gatherers = new ArrayList<>();
        producers = new ArrayList<>();
        //TODO: this needs to go through worker manager
        for (int i = 0; i < 5; i++) {
            Worker w = new Worker();
            staff.add(w);
            productionRate += w.getProduction();
        }
        //create 2 melee units
    }

    public String getType(){
        return "Capital";
    }

    @Override
    public ArrayList<Worker> removeWorkersFromGathering(int num) {
        ArrayList<Worker> newList = new ArrayList<>();
        for (int i = 0; i < num && i < gatherers.size(); i++){
            newList.add(gatherers.get(i));
            gatherers.remove(i);
        }

        return  newList;
    }

    @Override
    public ArrayList<Worker> removeWorkersFromProduction(int num) {
        ArrayList<Worker> newList = new ArrayList<>();
        for (int i = 0; i < num && i < producers.size(); i++){
            newList.add(producers.get(i));
            producers.remove(i);
        }

        return newList;
    }

    @Override
    public void addWorkersToGathering(ArrayList<Worker> list) {
        gatherers.addAll(list);
    }

    @Override
    public void addWorkersToProduction(ArrayList<Worker> list) {
        producers.addAll(list);
    }
}
