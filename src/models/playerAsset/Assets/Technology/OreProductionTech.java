package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

public class OreProductionTech extends ProductionTech{

    public void apply(CombatAsset a){
        ResourceStructure s = (ResourceStructure)a;
        s.increaseProduction("ore",0.5);
    }
}
