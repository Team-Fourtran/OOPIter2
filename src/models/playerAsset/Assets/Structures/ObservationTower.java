package models.playerAsset.Assets.Structures;

/**
 * Created by Clay on 2/26/2017.
 */
public class ObservationTower extends Structure {
    public ObservationTower() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
    }

    public String getType(){
        return "Observation Tower";
    }

}
