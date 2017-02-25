package models.playerAsset;

/**
 * Created by Clay on 2/25/2017.
 */
public class Base extends Structure {

    public Base()

    {
        offDamage = 75;
        defDamage = 75;
        armor = 150;
        productionRate = 2;
        maxHealth = currentHealth = 200;
        upkeep = 1;
    }

    public String getType(){
        return "Base";
    }
}
