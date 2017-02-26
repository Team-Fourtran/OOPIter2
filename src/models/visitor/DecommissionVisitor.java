package models.visitor;

import models.playerAsset.*;

public class DecommissionVisitor implements PlayerVisitor {
    private PlayerAsset byebye;

    public DecommissionVisitor(PlayerAsset asset){
        this.byebye = asset;
    }

    @Override
    public void visitPlayer(Player player) {

    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {

    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        structureManager.removeStructure((Structure) byebye);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        unitManager.removeUnit((Unit) byebye);
    }
}
