package models.playerAsset;

import models.visitor.AssetVisitor;

import java.util.ArrayList;
import java.util.Arrays;

/*Defines an Army, consisting of two groups of Units and a Rally Point
  Battle group units have met at the Rally Point and are able to move together and fight
  Reinforcements are units on the way to a Rally Point
 */
public class Army extends PlayerAsset {
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

    @Override
    public void accept(AssetVisitor v) {
        v.visitArmy(this);
        for (Unit u : reinforcements){
            u.accept(v);
        }
    }

}
