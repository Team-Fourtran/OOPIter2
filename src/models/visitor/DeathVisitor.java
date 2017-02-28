package models.visitor;


import models.assetOwnership.GameMap;
import models.playerAsset.*;

public class DeathVisitor implements AssetVisitor{
    private GameMap map;
    private Player player;

    public DeathVisitor(GameMap map, Player player){
        this.map = map;
        this.player = player;
    }

    @Override
    public void visitUnit(Unit unit) {
        unit.accept(
                new MapDecommissionVisitor(map, player)
        );
    }

    @Override
    public void visitArmy(Army army) {
        map.removeAssetFromMap(army);
        for( Unit _u : army.getBattleGroup()){
            new PlayerDecommissionVisitor(map, _u).visitUnitManager(player.getUnits());
        }
        new PlayerDecommissionVisitor(map, army).visitArmyManager(player.getArmies());
    }

    @Override
    public void visitStructure(Structure structure) {
        structure.accept(
                new MapDecommissionVisitor(map, player)
        );
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {

    }

    @Override
    public void visitWorker(Worker worker) {

    }
}
