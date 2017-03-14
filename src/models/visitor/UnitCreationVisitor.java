package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Assets.UnitManager;

public class UnitCreationVisitor implements PlayerVisitor{
    private GameMap map;
    private Structure structure;
    private String unitType;
    private Player player;

    public UnitCreationVisitor(GameMap map, Structure structure, String unitType){
        this.map = map;
        this.structure = structure;
        this.unitType = unitType;
    }

    @Override
    public void visitPlayer(Player player) {
        this.visitStructureManager(player.getStructures());
        this.visitUnitManager(player.getUnits());
        this.player = player;
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
    	Unit u = unitManager.addNewUnit(unitType);
    	PlayerAssetOwnership.addPlayerAsset(player, u);
        map.addAssetToMap(
                u,
                structure
        );
    }
}
