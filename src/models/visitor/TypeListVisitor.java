package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Units.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TypeListVisitor implements SpecificAssetVisitor {
    Map<String, ArrayList<Unit>> unitMap = new HashMap<>();
    Map<String, ArrayList<Structure>> structureMap = new HashMap<>();
    Map<String, ArrayList<Army>> armyMap = new HashMap<>();

    public Map<String, ArrayList<Unit>> getUnitMap(){
        return unitMap;
    }
    public Map<String, ArrayList<Structure>> getStructureMap(){
        return structureMap;
    }
    public Map<String, ArrayList<Army>> getArmyMap(){
        return armyMap;
    }

    @Override
    public void visitUnit(Unit unit) {

    }

    @Override
    public void visitArmy(Army army) {
        ArrayList<Army> newList = new ArrayList<>();
        newList.add(army);
        if(armyMap.containsKey("Entire Army")){
            ArrayList<Army> temp = armyMap.get("Entire Army");
            temp.addAll(newList);
            armyMap.replace("Entire Army", temp);
            return;
        }
        armyMap.put("Entire Army", newList);
//
//        ArrayList<Unit> bg = army.getBattleGroup();
//        ArrayList<Unit> rf = army.getReinforcements();
//        armyMap.put("Battle Group", bg);
//        armyMap.put("Reinforcements", rf);
//        bg.addAll(rf);
//        armyMap.put("Entire Army", bg);
    }

    @Override
    public void visitStructure(Structure structure) {

    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {

    }

    @Override
    public void visitCapital(Capital capital) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(capital);
        if(structureMap.containsKey("capital")){
            ArrayList<Structure> temp = structureMap.get("capital");
            temp.addAll(newList);
            structureMap.replace("capital", temp);
            return;
        }
        structureMap.put("capital", newList);
    }

    @Override
    public void visitFarm(Farm farm) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(farm);
        if(structureMap.containsKey("farm")){
            ArrayList<Structure> temp = structureMap.get("farm");
            temp.addAll(newList);
            structureMap.replace("farm", temp);
            return;
        }
        structureMap.put("farm", newList);
    }

    @Override
    public void visitFort(Fort fort) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(fort);
        if(structureMap.containsKey("fort")){
            ArrayList<Structure> temp = structureMap.get("fort");
            temp.addAll(newList);
            structureMap.replace("fort", temp);
            return;
        }
        structureMap.put("fort", newList);
    }

    @Override
    public void visitMine(Mine mine) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(mine);
        if(structureMap.containsKey("mine")){
            ArrayList<Structure> temp = structureMap.get("mine");
            temp.addAll(newList);
            structureMap.replace("mine", temp);
            return;
        }
        structureMap.put("mine", newList);
    }

    @Override
    public void visitObservationTower(ObservationTower observationTower) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(observationTower);
        if(structureMap.containsKey("observationTower")){
            ArrayList<Structure> temp = structureMap.get("observationTower");
            temp.addAll(newList);
            structureMap.replace("observationTower", temp);
            return;
        }
        structureMap.put("observationTower", newList);
    }

    @Override
    public void visitPowerPlant(PowerPlant powerPlant) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(powerPlant);
        if(structureMap.containsKey("powerPlant")){
            ArrayList<Structure> temp = structureMap.get("powerPlant");
            temp.addAll(newList);
            structureMap.replace("powerPlant", temp);
            return;
        }
        structureMap.put("powerPlant", newList);
    }

    @Override
    public void visitUniversity(University university) {
        ArrayList<Structure> newList = new ArrayList<>();
        newList.add(university);
        if(structureMap.containsKey("university")){
            ArrayList<Structure> temp = structureMap.get("university");
            temp.addAll(newList);
            structureMap.replace("university", temp);
            return;
        }
        structureMap.put("university", newList);
    }

    @Override
    public void visitColonist(Colonist colonist) {
        ArrayList<Unit> newList = new ArrayList<>();
        newList.add(colonist);
        if(unitMap.containsKey("colonist")){
            ArrayList<Unit> temp = unitMap.get("colonist");
            temp.addAll(newList);
            unitMap.replace("colonist", temp);
            return;
        }
        unitMap.put("colonist", newList);
    }

    @Override
    public void visitExplorer(Explorer explorer) {
        ArrayList<Unit> newList = new ArrayList<>();
        newList.add(explorer);
        if(unitMap.containsKey("explorer")){
            ArrayList<Unit> temp = unitMap.get("explorer");
            temp.addAll(newList);
            unitMap.replace("explorer", temp);
            return;
        }
        unitMap.put("explorer", newList);
    }

    @Override
    public void visitMeleeUnit(MeleeUnit meleeUnit) {
        ArrayList<Unit> newList = new ArrayList<>();
        newList.add(meleeUnit);
        if(unitMap.containsKey("meleeUnit")){
            ArrayList<Unit> temp = unitMap.get("meleeUnit");
            temp.addAll(newList);
            unitMap.replace("meleeUnit", temp);
            return;
        }
        unitMap.put("meleeUnit", newList);
    }

    @Override
    public void visitRangedUnit(RangedUnit rangedUnit) {
        ArrayList<Unit> newList = new ArrayList<>();
        newList.add(rangedUnit);
        if(unitMap.containsKey("rangedUnit")){
            ArrayList<Unit> temp = unitMap.get("rangedUnit");
            temp.addAll(newList);
            unitMap.replace("rangedUnit", temp);
            return;
        }
        unitMap.put("rangedUnit", newList);
    }
}
