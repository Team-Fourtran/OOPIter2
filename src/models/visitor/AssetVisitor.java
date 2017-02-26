package models.visitor;

import models.playerAsset.*;

public interface AssetVisitor {
    void visitUnit(Unit unit);
    void visitArmy(Army army);
    void visitStructure(CombatAsset structure);
    void visitRallyPoint(RallyPoint rallyPoint);
}


//class AttackVisitor implements AssetVisitor{
//
//}
//
//class HealingVisitor implements AssetVisitor{
//
//}