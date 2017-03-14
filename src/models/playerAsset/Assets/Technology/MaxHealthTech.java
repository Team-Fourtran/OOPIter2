package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */
public class MaxHealthTech implements Technology {

    public void apply(CombatAsset a){
        a.setMaxHealth(10 + a.getMaxHealth());
    }
}
