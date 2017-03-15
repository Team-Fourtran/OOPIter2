package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

public class EfficiencyTech implements Technology {

    public void apply(CombatAsset a){
        a.setUpkeep(a.getUpkeep()-10);
    }
}
