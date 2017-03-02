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
        setCurrentHealth(0);
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

    public double getMovementSpeed(){
        double minMoves = 100;
        for (Unit _u : battleGroup){
            if (_u.getMovementTurns() < minMoves)
                minMoves = _u.getMovementTurns();
        }
        return minMoves;
    }

    public void getArmyStats(){
        int current, max, arm;
        current = max = arm = 0;
        for (Unit _u : battleGroup){
            current += _u.getCurrentHealth();
            max = _u.getMaxHealth();
            arm += _u.getArmor();
        }
        setCurrentHealth(current);
        setMaxHealth(max);
        setArmor(arm);
    }

    public void addToBattleGroup(Unit u){
        battleGroup.add(u);
        reinforcements.remove(u);
        u.clearQueue();
        setMaxHealth((this.getMaxHealth() + u.getMaxHealth()));
        setCurrentHealth((this.getCurrentHealth() + u.getCurrentHealth()));
    }

    public void addReinforcement(Unit u){
        reinforcements.add(u);
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
    }
}
