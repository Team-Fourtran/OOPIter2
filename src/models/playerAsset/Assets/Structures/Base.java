package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

/**
 * Created by Clay on 2/25/2017.
 */
public class Base extends Structure {

    public Base()

    {
        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        staff = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Worker w = new Worker();
            staff.add(w);
            productionRate += w.getProduction();
        }
        //create 2 melee units
    }

    public String getType(){
        return "Base";
    }
}
