package models.visitor;


import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.Radius;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.ResourceStructure;
import models.playerAsset.Assets.Structures.Structure;

public class StructureCreationVisitor implements PlayerVisitor{
    private GameMap map;
    private RallyPoint rallyPoint;
    private String structureType;
    private Player player;
    private Structure creation;

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
        this.player = player;
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        //TODO: check if army is able to create?
    	// yes, it needs workers to create a structure
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        this.creation = structureManager.createStructure(structureType, map.searchForTileAssociation(rallyPoint));
        PlayerAssetOwnership.addPlayerAsset(player, creation);
        map.addAssetToMap(creation, rallyPoint.getArmy());
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        //Don't think anything happens here
    }

    public Structure getCreation(){
        return creation;
    }
}
