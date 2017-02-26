package models.playerAsset.Assets;

import models.playerAsset.Assets.Units.Unit;
import models.visitor.AssetVisitor;

import java.util.ArrayList;
import java.util.Arrays;

/*Defines an Army, consisting of two groups of Units and a Rally Point
  Battle group units have met at the Rally Point and are able to move together and fight
  Reinforcements are units on the way to a Rally Point
 */
//TODO: Add Army Iterators

public class Army extends CombatAsset {
    private ArrayList<Unit> battleGroup = new ArrayList<>();
    private ArrayList<Unit> reinforcements = new ArrayList<>();

    public Army(Unit ... units) {
        reinforcements.addAll(Arrays.asList(units));
    }

    public void addToBattleGroup(Unit u){
        battleGroup.add(u);
        reinforcements.remove(u);
        u.clearQueue();
    }

    public boolean hasBattleGroup(){
        return !battleGroup.isEmpty();
    }

    public boolean includes(Unit u){
        for (Unit _unit : battleGroup){
            if (u == _unit){
                return true;
            }
        }
        for (Unit _unit : reinforcements) {
            if (u == _unit) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Unit> getBattleGroup(){
        return battleGroup;
    }

    public ArrayList<Unit> getReinforcements(){
        return reinforcements;
    }

    public void accept(AssetVisitor v) {
        v.visitArmy(this);
//        for (Unit u : reinforcements){
//            u.accept(v);
//        }
    }

    //    //method to check if an army has a colonist to make a structure
//    public String hasColonist() {
//        for (Unit i: battleGroup)
//            if (i instanceof Colonist)
//                return i.getID();
//        return "";
//    }
//
//    //after a structure is made, remove the colonist from the army
//    public void removeColonist() {
//        Unit removed = null;
//        for (Unit i: battleGroup) {
//            if (i instanceof Colonist)
//                removed = i;
//        }
//        battleGroup.remove(removed);
//    }
}
