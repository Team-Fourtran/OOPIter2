package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */
public abstract class ProductionTech implements Technology {

    public abstract void apply(CombatAsset a);
}
