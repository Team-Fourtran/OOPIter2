package models.visitor;

import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Units.Colonist;
import models.playerAsset.Assets.Units.Explorer;
import models.playerAsset.Assets.Units.MeleeUnit;
import models.playerAsset.Assets.Units.RangedUnit;

public interface SpecificAssetVisitor extends AssetVisitor{
    void visitCapital(Capital capital);
    void visitFarm(Farm farm);
    void visitFort(Fort fort);
    void visitMine(Mine mine);
    void visitObservationTower(ObservationTower observationTower);
    void visitPowerPlant(PowerPlant powerPlant);
    void visitUniversity(University university);

    void visitColonist(Colonist colonist);
    void visitExplorer(Explorer explorer);
    void visitMeleeUnit(MeleeUnit meleeUnit);
    void visitRangedUnit(RangedUnit rangedUnit);
}
