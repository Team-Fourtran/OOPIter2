package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.*;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.tileInfo.Normal;

public class AttackVisitor implements AssetVisitor{
    private Player givingPlayer;
    private Player receivingPlayer;
    private GameMap map;
    private CombatAsset giver;
    private TileAssociation receiver;
    private int distance;

    public AttackVisitor(Player givingPlayer, Player receivingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver, int distance){
        this.givingPlayer = givingPlayer;
        this.receivingPlayer = receivingPlayer;
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
            unit.accept(
                    new DeathVisitor(map, receivingPlayer)
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
            //Structure is Dead!
            structure.accept(
                    new DeathVisitor(map, receivingPlayer)
            );
            giver.clearQueue();
            //TODO: EXPLOSION
        }
        else{
            //Still alive!
            //For defDamage allocation:
            if (structure.getDefDamage(distance) > 0){
                giver.depleteHealth(structure.getDefDamage(distance));
                if (giver.getCurrentHealth() <= 0 ){
                    //Dead!
                    giver.accept(
                            new DeathVisitor(map, givingPlayer)
                    );
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
}
