package models.visitor;

import models.playerAssetNew.*;

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