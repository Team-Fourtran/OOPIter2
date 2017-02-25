package models.visitor;

//Manager should be abstract
import models.playerAsset.ArmyManager;
import models.playerAsset.StructureManager;
import models.playerAsset.UnitManager;

public interface PlayerVisitor {
    void visitArmyManager(ArmyManager armyManager);
    void visitStructureManager(StructureManager structureManager);
    void visitUnitManager(UnitManager unitManager);
}

