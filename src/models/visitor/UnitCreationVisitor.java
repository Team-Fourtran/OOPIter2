package models.visitor;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.UnitManager;

public class UnitCreationVisitor implements PlayerVisitor{
    private GameMap map;
    private Structure structure;
    private String unitType;

    public UnitCreationVisitor(GameMap map, Structure structure, String unitType){
        this.map = map;
        this.structure = structure;
        this.unitType = unitType;
    }

    @Override
    public void visitPlayer(Player player) {
        this.visitStructureManager(player.getStructures());
        this.visitUnitManager(player.getUnits());
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        //TODO: CreateUnitCommand might be able to specify an army to reinforce
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        //TODO: Deplete resources?
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        map.addAssetToMap(
                unitManager.addNewUnit(unitType),
                structure
        );
    }
}
