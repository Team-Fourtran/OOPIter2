package models.visitor;

import models.ctrlCommand.*;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Units.*;
import models.playerAsset.Iterators.CommandIterator;

import java.util.ArrayList;

public class CommandListVisitor implements SpecificAssetVisitor{

    private ArrayList<CTRLCommand> cmdList;

    public CommandListVisitor(){
        cmdList = new ArrayList<>();
    }

    @Override
    public void visitUnit(Unit unit) {
        //Applicable to all units
        if (!unit.getPoweredUp()){
            cmdList.clear();
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLReinforceArmyCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
    }

    @Override
    public void visitArmy(Army army) {
        //Applicable to all armies
        if (!army.getPoweredUp()){
            cmdList.clear();
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLAttackCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
        cmdList.add(new CTRLCancelQueuedOrders());
    }

    @Override
    public void visitStructure(Structure structure) {
        //Applicable to all Structures
        if (!structure.getPoweredUp()){
            cmdList.clear();
            cmdList.add(new CTRLPowerUpCommand());
        }
        else{
            cmdList.add(new CTRLPowerDownCommand());
            cmdList.add(new CTRLHealCommand());
            cmdList.add(new CTRLDecommissionCommand());
        }
        cmdList.add(new CTRLCancelQueuedOrders());
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
        cmdList.add(new CTRLBuildCommand());
        cmdList.add(new CTRLCancelQueuedOrders());
        cmdList.add(new CTRLMoveRallyPointCommand());
        cmdList.add(new CTRLPickUpWorkers());
    }

    public CommandIterator getIterator(){
        return new CommandIterator(cmdList);
    }

    @Override
    public void visitCapital(Capital capital) {
        cmdList.add(new CTRLCreateUnitCommand());
        this.visitStructure(capital);
    }

    @Override
    public void visitFarm(Farm farm) {
        this.visitStructure(farm);
    }

    @Override
    public void visitFort(Fort fort) {
        cmdList.add(new CTRLAttackCommand());
        cmdList.add(new CTRLCreateUnitCommand());
        this.visitStructure(fort);
    }

    @Override
    public void visitMine(Mine mine) {
        this.visitStructure(mine);
    }

    @Override
    public void visitObservationTower(ObservationTower observationTower) {
        this.visitStructure(observationTower);
    }

    @Override
    public void visitPowerPlant(PowerPlant powerPlant) {
        this.visitStructure(powerPlant);
    }

    @Override
    public void visitUniversity(University university) {
        this.visitStructure(university);
    }

    @Override
    public void visitColonist(Colonist colonist) {
        cmdList.add(new CTRLCreateCapitalCommand());
        this.visitUnit(colonist);
    }

    @Override
    public void visitExplorer(Explorer explorer) {
        this.visitUnit(explorer);
    }

    @Override
    public void visitMeleeUnit(MeleeUnit meleeUnit) {
        this.visitUnit(meleeUnit);
    }

    @Override
    public void visitRangedUnit(RangedUnit rangedUnit) {
        this.visitUnit(rangedUnit);
    }
}
