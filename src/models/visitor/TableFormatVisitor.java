package models.visitor;

import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.UnitManager;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.Iterator2;
import views.NonEditableTable;

import java.util.ArrayList;

public class TableFormatVisitor implements PlayerVisitor {
    private String[] unitColumnStats = {"ID", "Type", "Offensive Damage",
            "Defensive Damage", "Armor", "Max Health", "Current Health", "Upkeep", "Radius"};

    private String[] structureColumnStats = {"ID", "Type", "Offensive Damage",
            "Defensive Damage", "Armor", "Max Health", "Current Health", "Upkeep", "Radius"};
    private NonEditableTable formattedTable;

    public TableFormatVisitor(){
    }

    @Override
    public void visitPlayer(Player player) {

    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {

    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        Iterator2<Structure> iter = structureManager.makeIterator();
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
        formatStructures(list);
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        Iterator2<Unit> iter = unitManager.makeIterator();
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
        formatUnits(list);
    }

    private void formatUnits(ArrayList<Unit> list){
        String[][] unitData = new String[list.size()][9];
        for(int num = 0; num < list.size(); num++){
            unitData[num][0] = list.get(num).getID();
            unitData[num][1] = list.get(num).getType();
            unitData[num][2] = Integer.toString(list.get(num).getOffDamage(0));
            unitData[num][3] = Integer.toString(list.get(num).getDefDamage(0));
            unitData[num][4] = Integer.toString(list.get(num).getArmor());
            unitData[num][5] = Integer.toString(list.get(num).getMaxHealth());
            unitData[num][6] = Integer.toString(list.get(num).getCurrentHealth());
            unitData[num][7] = Integer.toString(list.get(num).getUpkeep());
            unitData[num][8] = Integer.toString(list.get(num).getRadiusOfInfluence());
        }
        this.formattedTable = new NonEditableTable(unitData, unitColumnStats);
    }

    private void formatStructures(ArrayList<Structure> list){
        String[][] structureData = new String[list.size()][9];
        for(int num = 0; num < list.size(); num++){
            structureData[num][0] = list.get(num).getID();
            structureData[num][1] = list.get(num).getType();
            structureData[num][2] = Integer.toString(list.get(num).getOffDamage(0));
            structureData[num][3] = Integer.toString(list.get(num).getDefDamage(0));
            structureData[num][4] = Integer.toString(list.get(num).getArmor());
            structureData[num][5] = Integer.toString(list.get(num).getMaxHealth());
            structureData[num][6] = Integer.toString(list.get(num).getCurrentHealth());
            structureData[num][7] = Integer.toString(list.get(num).getUpkeep());
            structureData[num][8] = Integer.toString(list.get(num).getRadiusOfInfluence());
        }
        this.formattedTable = new NonEditableTable(structureData, structureColumnStats);
    }

    public NonEditableTable getFormattedTable(){
        return formattedTable;
    }
}
