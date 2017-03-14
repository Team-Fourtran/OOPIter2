package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

public class PlayerDecommissionVisitor implements PlayerVisitor {
    private PlayerAsset byebye;
    private GameMap map;

    public PlayerDecommissionVisitor(GameMap map, PlayerAsset asset){
        this.map = map;
        this.byebye = asset;
    }

    @Override
    public void visitPlayer(Player player) {

    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        byebye.clearQueue();
        PlayerAsset rp = armyManager.getRallyPoint((Army) byebye);
        map.removeAssetFromMap(rp);
        armyManager.removeArmy((Army) byebye);
        PlayerAssetOwnership.removePlayerAsset(byebye, rp);
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        byebye.clearQueue();
        structureManager.removeStructure((Structure) byebye);
        PlayerAssetOwnership.removePlayerAsset(byebye);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        byebye.clearQueue();
        unitManager.removeUnit((Unit) byebye);
        PlayerAssetOwnership.removePlayerAsset(byebye);
        //TODO remove from army if a reinforcement
        // TODO also have the army update its influence radius if a unit is removed
    }
}
