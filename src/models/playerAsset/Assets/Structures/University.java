package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

public class University extends Structure{

    public University() {

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        staff = new ArrayList<>();
    }
}
