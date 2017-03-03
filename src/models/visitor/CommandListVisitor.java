package models.visitor;

import models.ctrlCommand.*;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.CommandIterator;

import java.util.ArrayList;

public class CommandListVisitor implements AssetVisitor{

    private ArrayList<CTRLCommand> cmdList;

    public CommandListVisitor(){
    }

    @Override
    public void visitUnit(Unit unit) {
        if (!unit.getPoweredUp()){
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLReinforceArmyCommand());
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
    }

    @Override
    public void visitArmy(Army army) {
        if (!army.getPoweredUp()){
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLAttackCommand());
            cmdList.add(new CTRLMoveRallyPointCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
        cmdList.add(new CTRLCancelQueuedOrders());
    }

    @Override
    public void visitStructure(Structure structure) {
        if (!structure.getPoweredUp()){
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLAttackCommand());
            cmdList.add(new CTRLHealCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
        cmdList.add(new CTRLCancelQueuedOrders());
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        cmdList.add(new CTRLCancelQueuedOrders());
    }

    public CommandIterator getIterator(){
        return new CommandIterator(cmdList);
    }
}
