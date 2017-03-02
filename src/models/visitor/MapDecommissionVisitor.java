package models.visitor;

import models.assetOwnership.GameMap;
import models.command.Command;
import models.playerAsset.*;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Assets.Worker;

public class MapDecommissionVisitor implements AssetVisitor{
    private GameMap map;
    private Player player;

    public MapDecommissionVisitor(GameMap map, Player p){
        this.map = map;
        this.player = p;
    }


    @Override
    public void visitUnit(Unit unit) {
        map.removeAssetFromMap(unit);
        new PlayerDecommissionVisitor(map, unit).visitUnitManager(player.getUnits());
    }

    @Override
    public void visitArmy(Army army) {
        army.addCommand(
                new Command() {
                    @Override
                    public void execute() {
                        //Decommission ENTIRE army
                        map.removeAssetFromMap(army);
                        for( Unit _u : army.getReinforcements()){
                            map.removeAssetFromMap(_u);
                            new PlayerDecommissionVisitor(map, _u).visitUnitManager(player.getUnits());
                        }
                        for( Unit _u : army.getBattleGroup()){
                            new PlayerDecommissionVisitor(map, _u).visitUnitManager(player.getUnits());
                        }
                        new PlayerDecommissionVisitor(map, army).visitArmyManager(player.getArmies());
                    }

                    @Override
                    public double getTurns() {
                        return 1;
                    }
                }
        );
    }

    @Override
    public void visitStructure(Structure structure) {
        structure.addCommand(
                new Command() {
                    @Override
                    public void execute() {
                        map.removeAssetFromMap(structure);
                        new PlayerDecommissionVisitor(map, structure).visitStructureManager(player.getStructures());
                    }

                    @Override
                    public double getTurns() {
                        return 1;
                    }
                }
        );

    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        //Nothing
    }
}
