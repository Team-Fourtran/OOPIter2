package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.JoinBattleGroupCommand;
import models.command.MoveCommand;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

import java.util.ArrayList;

public class ReinforceArmyVisitor implements AssetVisitor{
    private Unit unit;
    private GameMap map;
    private RallyPoint rallyPoint;
    private Player player;

    public ReinforceArmyVisitor(GameMap map, Player player, Unit unit){
        this.unit = unit;
        this.map = map;
        this.player = player;
    }

    @Override
    public void visitUnit(Unit unit) {
        unit.clearQueue();
        ArrayList<TileAssociation> path = map.generatePath(unit, rallyPoint);
        TileAssociation cur = path.remove(0);
        for (TileAssociation next : path){
            unit.addCommand(
                    new MoveCommand(map, player, unit, cur, next)
            );
            cur = next;
        }
    }

    @Override
    public void visitArmy(Army army) {
        army.addReinforcement(unit);
        army.addUniversalCommand(
                new JoinBattleGroupCommand(army, unit, map)
        );
        this.visitUnit(unit);
    }

    @Override
    public void visitStructure(Structure structure) {

    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        this.rallyPoint = rallyPoint;
        this.visitArmy(rallyPoint.getArmy());
    }
}
