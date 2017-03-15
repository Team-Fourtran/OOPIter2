package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

/**
 * Created by Clay on 3/14/2017.
 */
public class FoodProductionTech extends ProductionTech {

    public void apply(CombatAsset a){
        ResourceStructure s = (ResourceStructure)a;
        s.increaseProduction("food",0.5);
    }
}
