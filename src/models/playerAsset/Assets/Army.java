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
        currentHealth = 0;
    }

    @Override
    public int getOffDamage(int distance){
        int totalDamage = 0;
        for (Unit _u : battleGroup){
            totalDamage += _u.getOffDamage(distance);
        }
        return totalDamage;
    }

    @Override
    public int getDefDamage(int distance){
        int totalDamage = 0;
        for (Unit _u : battleGroup){
            totalDamage += _u.getDefDamage(distance);
        }
        return totalDamage;
    }

    @Override
    public void depleteHealth(int value){

    }

    public void addToBattleGroup(Unit u){
        battleGroup.add(u);
        reinforcements.remove(u);
        u.clearQueue();
        maxHealth += u.getMaxHealth();
        currentHealth += u.getCurrentHealth();
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
}
