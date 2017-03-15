package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

/**
 * Created by Clay on 3/2/2017.
 */
public class DensityTech implements Technology {

    @Override
    public void apply(CombatAsset a) {
        ResourceStructure s = (ResourceStructure)a;
        s.setWorkerDensity(5 + s.getWorkerDensity());
    }

    public String toString(){return "WorkerDensityTech";}
}
