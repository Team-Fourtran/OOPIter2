package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

public class EfficiencyTech extends StatTechnology {

    public void apply(CombatAsset a){
        a.setUpkeep(a.getUpkeep()-10);
    }
}
