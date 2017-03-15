package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */
public class ArmorTech implements Technology{

    public void apply(CombatAsset a){
        a.setArmor(10 + a.getArmor());
    }

    public String toString(){return "ArmorTech";}

}
