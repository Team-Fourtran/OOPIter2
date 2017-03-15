package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.TileAssociation;
import models.command.JoinBattleGroupCommand;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Units.Unit;

public class ArmyCreationVisitor implements PlayerVisitor{

    private TileAssociation location;
    private Unit[] units;
    private GameMap map;
    private Player player;

    public ArmyCreationVisitor(GameMap map, Player player, TileAssociation location, Unit ... units){
        this.player = player;
        this.location = location;
        this.units = units;
        this.map = map;
    }

    @Override
    public void visitPlayer(Player p) {
        this.player = p;
        this.visitArmyManager(player.getArmies());
        this.visitUnitManager(player.getUnits());
        this.visitStructureManager(player.getStructures());
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        //Should have Army Factory to return new army...
        Army newArmy = armyManager.formArmy(units);
        RallyPoint rp = armyManager.formRallyPoint(newArmy);
        PlayerAssetOwnership.addPlayerAsset(player, newArmy, rp);

        rp.setArmy(newArmy);
        map.addAssetToMap(rp, location);
        map.addAssetToMap(newArmy, location);
        for (Unit u : units){
            newArmy.addUniversalCommand(
                    new JoinBattleGroupCommand(newArmy, u, map)
            );
        }
        rp.accept( //TODO Bug here that adds stuff to TA twice
                new MovementVisitor(map, player, location)
        );

    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        //nothing
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
//        for (Unit u : units){
//            unitManager.removeUnit(u);
//        }
        //TODO Ask Clay what happens to the units here that are added to an army
    }
}
