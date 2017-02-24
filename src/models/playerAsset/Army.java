package src.models.playerAsset;

import java.util.*;

public class Army extends PlayerAsset {

    ArrayList<Unit> battleGroup = new ArrayList<Unit>();
    ArrayList<Unit> reinforcements = new ArrayList<Unit>();
    String rallyPoint;

    public Army(ArrayList<Unit> units) {
        for (Unit u: units){
            reinforcements.add(u);
        }
    }

    //return all units in the army from both lists
    public ArrayList<Unit> getAllUnits(){
        ArrayList<Unit> newList = new ArrayList<>();
        newList.addAll(battleGroup);
        newList.addAll(reinforcements);
        return newList;
    }

    //method to check if an army has a colonist to make a structure
    public String hasColonist() {
        for (Unit i: battleGroup)
            if (i instanceof Colonist)
                return i.getID();
        return "";
    }

    //after a structure is made, remove the colonist from the army
    public void removeColonist() {
        Unit removed = null;
        for (Unit i: battleGroup) {
            if (i instanceof Colonist)
                removed = i;
        }
        battleGroup.remove(removed);
    }

    //see if any reinforcements arrived at the rally point
    //to be called each turn
    public void updateArmyTypes(){
        Unit removeMe = null;
        for (Unit u: reinforcements) {
            if (u.getLocation().equals(rallyPoint)){
                battleGroup.add(u);
                removeMe = u;
            }
        }

        reinforcements.remove(removeMe);
    }

    //check if a unit exists in this army
    //potentially useless function
    public boolean getUnit(Unit u){
        ArrayList<Unit> units = getAllUnits();
        for (Unit unit: units)
            if (u == unit)
                return true;
        return false;
    }
}
