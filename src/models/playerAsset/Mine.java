package models.playerAsset;

import java.util.ArrayList;

public class Mine extends Structure{

    public Mine() {

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        staff = new ArrayList<>();

    }
}
