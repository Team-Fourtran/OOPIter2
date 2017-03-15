package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.ModifyWorkersCommand;
import models.command.MoveCommand;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

import java.util.ArrayList;

public class MovementVisitor implements AssetVisitor{
    private TileAssociation destination;
    private GameMap gameMap;
    private Player player;
    private ArrayList<TileAssociation> armyPath;
    private boolean pickingUpWorkers = false;
    private boolean droppingOffWorkers = false;
    private int numWorkers;
    private Structure workersStructure;

    public MovementVisitor(GameMap gameMap, Player player, TileAssociation destination){
        this.destination = destination; //Ask about static methods...!
        this.player = player;
        this.gameMap = gameMap;
    }

    public void visitUnit(Unit unit){
        unit.clearQueue();
        ArrayList<TileAssociation> path = gameMap.generatePath(unit, destination);
        TileAssociation cur = path.remove(0);
        for (TileAssociation next : path){
            unit.addCommand(
                    new MoveCommand(gameMap, player, unit, cur, next)
            );
            cur = next;
        }
    }

    @Override
    public void visitArmy(Army army) {
        if (!army.hasBattleGroup()){
            gameMap.generateImmediateMovement(army, destination);
        }
        else{
            //ArrayList<TileAssociation> path = gameMap.generatePath(army, destination);
            TileAssociation cur = armyPath.remove(0);
            for (TileAssociation next : armyPath){
                //if(player.getArmies())
                army.addCommand(
                        new MoveCommand(gameMap, player, army, cur, next)
                );
                cur = next;
            }
            if(pickingUpWorkers){
                army.addCommand(
                        new ModifyWorkersCommand(true, gameMap, army, this.workersStructure, this.numWorkers)
                );
            }
            else if(droppingOffWorkers){
                army.addCommand(
                        new ModifyWorkersCommand(false, gameMap, army, this.workersStructure, this.numWorkers)
                );
            }
        }
        for (Unit _u : army.getReinforcements()){
            this.visitUnit(_u);
        }
    }

    @Override
    public void visitStructure(Structure structure) {
        System.out.println("Cannot move structures!");
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        armyPath = gameMap.generatePath(rallyPoint, destination);
        gameMap.generateImmediateMovement(rallyPoint, destination);
        this.visitArmy(rallyPoint.getArmy());
    }

    public void setNeedWorkers(boolean pickingUp, Structure workersStructure, int numWorkers){
        if(pickingUp){
            this.pickingUpWorkers = true;
        }
        else{
            this.droppingOffWorkers = true;
        }
        this.numWorkers = numWorkers;
        this.workersStructure = workersStructure;
    }
}