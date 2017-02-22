package models.playerAssetNew;

import models.visitor.AssetVisitor;

import java.util.ArrayList;
import java.util.Arrays;

/*Defines an Army, consisting of two groups of Units and a Rally Point
  Battle group units have met at the Rally Point and are able to move together and fight
  Reinforcements are units on the way to a Rally Point
 */
public class Army extends PlayerAsset {
    private ArrayList<Unit> battleGroup = new ArrayList<>();
    ArrayList<Unit> reinforcements = new ArrayList<>();

    public Army(Unit initial, Unit ... units) {
        battleGroup.add(initial);
        reinforcements.addAll(Arrays.asList(units));
    }

    public void addToBattleGroup(Unit u){
        u.clearQueue();
    }

    @Override
    public void accept(AssetVisitor v) {
        v.visitArmy(this);
        for (Unit u : reinforcements){
            u.accept(v);
        }
    }

}
