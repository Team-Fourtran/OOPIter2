package models.visitor;

import models.playerAsset.*;

public interface AssetVisitor {
    void visitUnit(Unit unit);
    void visitArmy(Army army);
    void visitStructure(Structure structure);
    void visitRallyPoint(RallyPoint rallyPoint);
    void visitWorker(Worker worker);
}


//class AttackVisitor implements AssetVisitor{
//
//}
//
//class HealingVisitor implements AssetVisitor{
//
//}