package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

/**
 * Created by Clay on 3/2/2017.
 */
public class WorkRadiusTech implements Technology {

    @Override
    public void apply(CombatAsset a) {
        ResourceStructure s = (ResourceStructure)a;
        s.setRadiusSize(1 + s.getWorkRadiusSize());
    }

    public String toString(){return "WorkRadiusTech";}
}
