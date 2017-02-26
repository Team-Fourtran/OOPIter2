package models.visitor;

//Manager should be abstract
import models.playerAsset.ArmyManager;
import models.playerAsset.Player;
import models.playerAsset.StructureManager;
import models.playerAsset.UnitManager;

public interface PlayerVisitor {
    void visitPlayer(Player player);
    void visitArmyManager(ArmyManager armyManager);
    void visitStructureManager(StructureManager structureManager);
    void visitUnitManager(UnitManager unitManager);
}

