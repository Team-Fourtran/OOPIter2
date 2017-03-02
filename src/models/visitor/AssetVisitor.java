package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

public interface AssetVisitor {
    void visitUnit(Unit unit);
    void visitArmy(Army army);
    void visitStructure(Structure structure);
    void visitRallyPoint(RallyPoint rallyPoint);
}