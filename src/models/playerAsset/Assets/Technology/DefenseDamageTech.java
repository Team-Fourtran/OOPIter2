package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */
public class DefenseDamageTech implements Technology {

    public void apply(CombatAsset a){
        a.setOffDamage(10 + a.getOffDamage(0));
    }
}
