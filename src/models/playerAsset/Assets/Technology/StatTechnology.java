package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.PlayerAsset;

public abstract class StatTechnology implements Technology{

    public abstract void apply(CombatAsset a);
}
