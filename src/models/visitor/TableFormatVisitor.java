package models.visitor;

import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.Iterator2;
import views.NonEditableTable;

import java.util.ArrayList;

public class TableFormatVisitor implements PlayerVisitor {
    private Player player;
    private String[] unitColumnStats = {"ID", "Type", "In Army", "Offensive Damage",
            "Defensive Damage", "Armor", "Max Health", "Current Health", "Upkeep", "Radius"};

    private String[] structureColumnStats = {"ID", "Type", "Offensive Damage",
            "Defensive Damage", "Armor", "Max Health", "Current Health", "Upkeep", "Radius"};
    private ArrayList<Unit> unitList;
    private ArrayList<Unit> armyList;
    private ArrayList<Structure> structureList;
    private NonEditableTable formattedTable;

    public TableFormatVisitor(Player p){
        this.player = p;
    }

    @Override
    public void visitPlayer(Player player) {

    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {
        Iterator2<Army> iter = armyManager.makeIterator();
        armyList = new ArrayList<>(0);
        iter.first();
        if (iter.current() == null){
            return;
        }
        ArrayList<Unit> armyList = new ArrayList<>();
        Army first = iter.current();
        while(true){
            for(Unit u : first.getBattleGroup()){
                armyList.add(u);
            }
            for(Unit u : first.getReinforcements()){
                armyList.add(u);
            }
            iter.next();
            if(iter.current() == first){
                break;
            }
        }
        this.armyList = armyList;
    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        Iterator2<Structure> iter = structureManager.makeIterator();
        structureList = new ArrayList<>(0);
        iter.first();
        if (iter.current() == null){
            return;
        }
        ArrayList<Structure> list = new ArrayList<>();
        list.add(iter.current());
        while(true){
            iter.next();
            if(list.contains(iter.current())){
                iter.nextType();
                if(list.contains(iter.current())){
                    break;
                }
            }
            list.add(iter.current());
        }
        this.structureList = list;
        formatStructures();
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        Iterator2<Unit> iter = unitManager.makeIterator();
        unitList = new ArrayList<>(0);
        iter.first();
        if (iter.current() == null){
            return;
        }
        ArrayList<Unit> list = new ArrayList<>();
        list.add(iter.current());
        while(true){
            iter.next();
            if(list.contains(iter.current())){
                iter.nextType();
                if(list.contains(iter.current())){
                    break;
                }
            }
            list.add(iter.current());
        }
        this.unitList = list;
        this.visitArmyManager(player.getArmies());
        formatUnits();
    }

    private void formatUnits(){
        int size = 0;
        if(!unitList.isEmpty()) {
            size += unitList.size();
            if (!armyList.isEmpty()) {
                size += armyList.size();
            }
        }
        String[][] unitData = new String[size][10];
        for (int num = 0; num < unitList.size(); num++) {
            if(armyList.contains(unitList.get(num))){
                unitData[num][2] = "YES";
            }
            else{
                unitData[num][2] = "NO";
            }
            unitData[num][0] = unitList.get(num).getID();
            unitData[num][1] = unitList.get(num).getType();
            unitData[num][3] = Integer.toString(unitList.get(num).getOffDamage(0));
            unitData[num][4] = Integer.toString(unitList.get(num).getDefDamage(0));
            unitData[num][5] = Integer.toString(unitList.get(num).getArmor());
            unitData[num][6] = Integer.toString(unitList.get(num).getMaxHealth());
            unitData[num][7] = Integer.toString(unitList.get(num).getCurrentHealth());
            unitData[num][8] = Integer.toString(unitList.get(num).getUpkeep());
            unitData[num][9] = Integer.toString(unitList.get(num).getRadiusOfInfluence());
        }
        this.formattedTable = new NonEditableTable(unitData, unitColumnStats);
    }

    private void formatStructures(){
        String[][] structureData = new String[structureList.size()][9];
        for(int num = 0; num < structureList.size(); num++){
            structureData[num][0] = structureList.get(num).getID();
            structureData[num][1] = structureList.get(num).getType();
            structureData[num][2] = Integer.toString(structureList.get(num).getOffDamage(0));
            structureData[num][3] = Integer.toString(structureList.get(num).getDefDamage(0));
            structureData[num][4] = Integer.toString(structureList.get(num).getArmor());
            structureData[num][5] = Integer.toString(structureList.get(num).getMaxHealth());
            structureData[num][6] = Integer.toString(structureList.get(num).getCurrentHealth());
            structureData[num][7] = Integer.toString(structureList.get(num).getUpkeep());
            structureData[num][8] = Integer.toString(structureList.get(num).getRadiusOfInfluence());
        }
        this.formattedTable = new NonEditableTable(structureData, structureColumnStats);
    }

    public NonEditableTable getFormattedTable(){
        return formattedTable;
    }
}
