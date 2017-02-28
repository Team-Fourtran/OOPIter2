package models.playerAsset.Assets.Structures;

/**
 * Created by Clay on 2/26/2017.
 */
public class ObservationTower extends Structure {
    public ObservationTower() {

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
    }
}
