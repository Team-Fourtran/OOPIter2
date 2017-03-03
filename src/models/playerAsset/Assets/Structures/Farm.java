package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

/**
 * Created by Clay on 2/26/2017.
 */
public class Farm extends Structure{

    public Farm() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
        staff = new ArrayList<>();

    }
}
