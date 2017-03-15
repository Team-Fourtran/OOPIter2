
package models.playerAsset.Assets;
import models.playerAsset.Assets.Units.*;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.specificTypeIterator;
import models.visitor.PlayerVisitor;
import models.visitor.TypeListVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* Manager for a Player's Units
   Helps create units and pass commands to them
 */
public class UnitManager implements Manager {
    private CopyOnWriteArrayList<Unit> unitList;
    final private UnitFactory factory;
    final int maxUnits = 25;
    final int maxUnitType = 10;
    static ArrayList<String> unitIDs = new ArrayList<>();

    public UnitManager() {
        unitList = new CopyOnWriteArrayList<>();
        factory = new UnitFactory();
        for (int i = 1; i <= 50; i++)
            unitIDs.add("u" + i);
    }

    public void removeUnit(PlayerAsset unit){
        unitList.remove(unit);
    }

    public Unit addNewUnit(String type){
        Unit newUnit = factory.makeUnit(type);
        newUnit.setID(unitIDs.get(0));
        unitIDs.remove(0);
        unitList.add(newUnit);
        return newUnit;
    }

    //if possible, execute a (movement) command in units
    public void executeCommands(){
        java.util.Iterator<Unit> iter = unitList.iterator();
        iter.forEachRemaining(Unit::executeCommand);
    }

    //reset the ability for a unit to move
    public void resetCommands(){
        java.util.Iterator<Unit> iter = unitList.iterator();
        iter.forEachRemaining(Unit::resetCommands);
    }

    //Should be in abstract class
    public void accept(PlayerVisitor v){
        v.visitUnitManager(this);
    }

    //recycle an ID of a unit who doesn't need one anymore
    @Override
    public void freeID(String assetID) {
        int escapee = Integer.parseInt(assetID.substring(assetID.lastIndexOf("u") + 1).trim());
        for (int i = 0; i < unitIDs.size(); i++) {
            String currentID = unitIDs.get(i);
            int id = Integer.parseInt(currentID.substring(currentID.lastIndexOf("u") + 1).trim());
            if (escapee < id) {
                unitIDs.add(i, assetID);
                break;
            }
        }
    }

    @Override
    public int calculateTotalUpkeep(){
        int totalUpkeep = 0;
        for (Unit u: unitList){
            totalUpkeep += u.getUpkeep();
        }
        return totalUpkeep;
    }

    public Iterator2<Unit> makeIterator(){
        return new Iterator2<Unit>() {
            private int current;
            private specificTypeIterator<Unit> entryIter;
            private ArrayList<Unit> currentUnitList;
            private Map<String, ArrayList<Unit>> map = new HashMap<>();

            @Override
            public Iterator2<Unit> first() {
                TypeListVisitor vis = new TypeListVisitor();
                for (Unit u : unitList){
                    u.accept(
                            vis
                    );
                }
                map = vis.getUnitMap();
                entryIter = new specificTypeIterator<>(map);
                entryIter.first();
                if (entryIter.current() == null){
                    currentUnitList = null;
                }
                else{
                    currentUnitList = entryIter.current().getValue();
                }
                current = 0;
                return this;
            }

            @Override
            public void next() {
                current += 1;
                current %= currentUnitList.size();
            }

            @Override
            public void prev() {
                current -= 1;
                if (current < 0){
                    current = currentUnitList.size()-1;
                }
            }

            @Override
            public Unit current() {
                if (currentUnitList == null){
                    return null;
                }
                return currentUnitList.get(current);
            }

            @Override
            public void nextType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.next();
                currentUnitList = entryIter.current().getValue();
            }

            @Override
            public void prevType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.prev();
                currentUnitList = entryIter.current().getValue();
            }

            @Override
            public String getElement() {
                return entryIter.current().getKey();
            }
        };
    }
}
