package models.visitor;

import models.assetOwnership.GameMap;
import models.playerAsset.*;

public class CapitalCreationVisitor implements PlayerVisitor{

    private Unit colonistToRemove;
    private GameMap map;

    public CapitalCreationVisitor(Unit unit, GameMap map){
        this.colonistToRemove = unit;
        this.map = map;
    }

    @Override
    public void visitPlayer(Player player) {
        this.visitStructureManager(player.getStructures());
        this.visitUnitManager(player.getUnits());
        //this.visitArmyManager(player.getArmies());
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        map.replaceAsset(
                colonistToRemove,
                structureManager.createStructure("capital")
        );
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {

    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        unitManager.removeUnit(colonistToRemove);   //Will this get removed from the Army?
    }
}
