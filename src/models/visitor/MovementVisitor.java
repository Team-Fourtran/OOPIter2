package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.MoveCommand;
import models.playerAssetNew.*;

import java.util.ArrayList;

public class MovementVisitor implements AssetVisitor{
    private TileAssociation destination;
    private GameMap gameMap;

    public MovementVisitor(GameMap gameMap, TileAssociation destination){
        this.destination = destination; //Ask about static methods...!
        this.gameMap = gameMap;
    }

    public void visitUnit(Unit unit){
        ArrayList<TileAssociation> path = gameMap.generatePath(unit, destination);
        TileAssociation cur = path.remove(0);
        for (TileAssociation next : path){
            unit.addCommand(
                    new MoveCommand(unit, cur, next)
            );
            cur = next;
        }
    }

    @Override
    public void visitArmy(Army army) {
        ArrayList<TileAssociation> path = gameMap.generatePath(army, destination);
        TileAssociation cur = path.remove(0);
        for (TileAssociation next : path){
            army.addCommand(
                    new MoveCommand(army, cur, next)
            );
            cur = next;
        }
    }

    @Override
    public void visitStructure(PlayerAsset structure) {
        System.out.println("Cannot move structures!");
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        gameMap.generateImmediateMovement(rallyPoint, destination);
    }
}