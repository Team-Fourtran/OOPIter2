package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.*;
import models.tileInfo.Normal;

public class AttackVisitor implements TileVisitor{
    private Player player;
    private GameMap map;
    private CombatAsset giver;
    private TileAssociation receiver;

    public AttackVisitor(Player p, GameMap map, CombatAsset giver, TileAssociation receiver){
        this.player = p;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void visitUnit(Unit unit) {
        //unit is not part of an army

        unit.depleteHealth(giver.getOffDamage());

        if ( unit.getCurrentHealth() <= 0 ){
            //Dead!
            map.removeAssetFromMap(unit);
            new DecommissionVisitor(unit).visitUnitManager(
                    player.getUnits()
            );
            //TODO: EXPLOSION
        }
        else{
            //Still alive!
            //Won't reciprocate b/c not in an army
        }
    }
    @Override
    public void visitStructure(Structure structure) {
        structure.depleteHealth(giver.getOffDamage());

        if ( structure.getCurrentHealth() <= 0 ){
            //Dead!
            map.removeAssetFromMap(structure);
            new DecommissionVisitor(structure).visitStructureManager(
                    player.getStructures()
            );
            //TODO: EXPLOSION
        }
        else{
            //Still alive!
            //For defDamage allocation:
//        if (unit.getDefDamage() > 0){
//            new AttackVisitor(map, receiver, unit);
//        }
        }
    }

    @Override
    public void visitArmy(Army army) {

    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        //Undefined
    }

    @Override
    public void visitWorker(Worker worker) {

    }

    @Override
    public void visitNormal(Normal normal) {

    }
}
