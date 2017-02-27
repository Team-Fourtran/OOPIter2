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
    private int distance;

    public AttackVisitor(Player p, GameMap map, CombatAsset giver, TileAssociation receiver, int distance){
        this.player = p;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
        this.distance = distance;
    }

    @Override
    public void visitUnit(Unit unit) {
        //unit is not part of an army

        unit.depleteHealth(giver.getOffDamage(distance));

        if ( unit.getCurrentHealth() <= 0 ){
            //Dead!
            map.removeAssetFromMap(unit);
            new DecommissionVisitor(unit).visitUnitManager(
                    player.getUnits()
            );
            giver.clearQueue();
            //TODO: EXPLOSION
        }
        else{
            //Still alive!
            //Won't reciprocate b/c not in an army
        }
    }
    @Override
    public void visitStructure(Structure structure) {
        structure.depleteHealth(giver.getOffDamage(distance));

        if ( structure.getCurrentHealth() <= 0 ){
            //Dead!
            map.removeAssetFromMap(structure);
            new DecommissionVisitor(structure).visitStructureManager(
                    player.getStructures()
            );
            giver.clearQueue();
            //TODO: EXPLOSION
        }
        else{
            //Still alive!
            //For defDamage allocation:
            if (structure.getDefDamage() > 0){
                giver.depleteHealth(structure.getOffDamage(distance));
                if (giver.getCurrentHealth() <= 0 ){
                    //Dead!
                    //TODO: WORK ON DECOMMISSIONING ARMIES AND AVOID TYPE-CHECKING BELOW
                    map.removeAssetFromMap(giver);

                }
            }
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
