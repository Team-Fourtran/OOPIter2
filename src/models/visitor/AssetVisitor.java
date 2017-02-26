package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Units.Unit;

public interface AssetVisitor {
    void visitUnit(Unit unit);
    void visitArmy(Army army);
    void visitStructure(PlayerAsset structure);
    void visitRallyPoint(RallyPoint rallyPoint);
}


//class AttackVisitor implements AssetVisitor{
//
//}
//
//class HealingVisitor implements AssetVisitor{
//
//}