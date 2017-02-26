package models.playerAsset;

import java.util.ArrayList;

/**
 * Created by Clay on 2/26/2017.
 */
public class PowerPlant extends Structure {

    public PowerPlant() {

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        staff = new ArrayList<>();

    }
}
