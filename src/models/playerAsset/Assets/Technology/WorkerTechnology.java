package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

public abstract class WorkerTechnology implements Technology{

    public abstract void apply(CombatAsset a);
}
