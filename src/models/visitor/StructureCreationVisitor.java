package models.visitor;


import models.assetOwnership.GameMap;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;

public class StructureCreationVisitor implements PlayerVisitor{
    private GameMap map;
    private RallyPoint rallyPoint;
    private String structureType;

    public StructureCreationVisitor(GameMap map, RallyPoint rallyPoint, String structureType){
        this.map = map;
        this.rallyPoint = rallyPoint;
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
        Structure s = structureManager.createStructure(structureType);
        //TODO: see if we can pass in the TileAssociation so we dont have to call this map method...
        map.addAssetToMap(s, rallyPoint);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        //Don't think anything happens here
    }
}
