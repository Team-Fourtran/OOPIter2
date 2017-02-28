package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Capital extends Structure {

    public Capital()
    {
        offDamage = 75;
        defDamage = 20;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        setRange(3);
        staff = new ArrayList<>();
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
