package models.visitor;

//Manager should be abstract
import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.UnitManager;

public interface PlayerVisitor {
    void visitPlayer(Player player);
    void visitArmyManager(ArmyManager armyManager);
    void visitStructureManager(StructureManager structureManager);
    void visitUnitManager(UnitManager unitManager);
}

