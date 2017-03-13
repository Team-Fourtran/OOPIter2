package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */
public class VisibilityTech extends StatTechnology {

    public void apply(CombatAsset a){
        a.setVisibility(1 + a.getVisibility());
    }

}
