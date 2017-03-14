package models.visitor;

import models.assetOwnership.GameMap;
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
        map.removeAssetFromMap(
                armyManager.getRallyPoint((Army) byebye)
        );
        armyManager.removeArmy((Army) byebye);
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        byebye.clearQueue();
        structureManager.removeStructure((Structure) byebye);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        byebye.clearQueue();
        unitManager.removeUnit((Unit) byebye);
        //TODO remove from army if a reinforcement
        // TODO also have the army update its influence radius if a unit is removed
    }
}
