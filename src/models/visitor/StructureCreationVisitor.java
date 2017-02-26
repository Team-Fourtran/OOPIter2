package models.visitor;


import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.UnitManager;
import models.playerAsset.Assets.Units.Unit;

public class StructureCreationVisitor implements PlayerVisitor{
    private String structureType;
    private Unit toRemove;

    public StructureCreationVisitor(String structureType){
        this.structureType = structureType;
    }

    @Override
    public void visitPlayer(Player player) {
        //TODO: Check resources?
        this.visitArmyManager(player.getArmies());
        this.visitStructureManager(player.getStructures());
        this.visitUnitManager(player.getUnits());
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        //TODO: check if army is able to create?
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        structureManager.createStructure(structureType);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        //Don't think anything happens here
    }
}
