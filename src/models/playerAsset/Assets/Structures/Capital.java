package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Capital extends ResourceStructure{


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

}
