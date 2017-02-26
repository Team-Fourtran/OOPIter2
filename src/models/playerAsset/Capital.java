package models.playerAsset;

public class Capital extends Structure{

    public void Capital(){
        offDamage = 75;
        defDamage = 75;
        armor = 150;
        productionRate = 2;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        setRange(2);
    }

}
