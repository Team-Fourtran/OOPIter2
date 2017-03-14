
package models.playerAsset.Assets;
import models.playerAsset.Assets.Units.*;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.TypeIterator2;
import models.visitor.PlayerVisitor;
import models.visitor.TypeListVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* Manager for a Player's Units
   Helps create units and pass commands to them
 */
public class UnitManager {
    private CopyOnWriteArrayList<Unit> unitList;
    final private UnitFactory factory;
    ArrayList<Unit> explorerList;
    ArrayList<Unit> colonistList;
    ArrayList<Unit> meleeList;
    ArrayList<Unit> rangedList;
    final int maxUnits = 25;
    final int maxUnitType = 10;
    static ArrayList<String> unitIDs = new ArrayList<>();
    ArrayList<Iterator<PlayerAsset>> unitIterators;

    public UnitManager() {
        unitList = new CopyOnWriteArrayList<>();
        explorerList = new ArrayList<>();
        colonistList = new ArrayList<>();
        meleeList = new ArrayList<>();
        rangedList = new ArrayList<>();
        factory = new UnitFactory();
        for (int i = 1; i <= 50; i++)
            unitIDs.add("u" + i);
//        unitIterators = new ArrayList<>();
//        unitIterators.add(makeIterator(explorerList));
//        unitIterators.add(makeIterator(colonistList));
//        unitIterators.add(makeIterator(meleeList));
//        unitIterators.add(makeIterator(rangedList));
    }

    public void removeUnit(Unit unit){
        unitList.remove(unit);
        explorerList.remove(unit);
        colonistList.remove(unit);
        meleeList.remove(unit);
        rangedList.remove(unit);
    }

    /*test method to grab iterator*/
//    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> getTypeIterator(){
//        return makeTypeIterator(unitIterators);
//    }

    public Unit addNewUnit(String type){
        Unit newUnit = factory.makeUnit(type);
        newUnit.setID(unitIDs.get(0));
        unitIDs.remove(0);
        addUnitToList(newUnit, type);
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

    public void addUnitToList(Unit u, String type){

        switch(type){
            case "explorer":
                unitList.add(u);
                explorerList.add((Explorer)u);
                break;
            case "colonist":
                unitList.add(u);
                colonistList.add((Colonist)u);
                break;
            case "melee":
                unitList.add(u);
                meleeList.add((MeleeUnit)u);
                break;
            case "ranged":
                unitList.add(u);
                rangedList.add((RangedUnit)u);
                break;
        }
    }

    //recycle an ID of a unit who doesn't need one anymore
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

    public int calculateTotalUpkeep(){
        int totalUpkeep = 0;
        for (Unit u: unitList){
            totalUpkeep += u.getUpkeep();
        }
        return totalUpkeep;
    }

    public TypeIterator2<Map.Entry<String, ArrayList<Unit>>> makeTypeIterator(Map<String, ArrayList<Unit>> map){
        return new TypeIterator2<Map.Entry<String,ArrayList<Unit>>>() {
            private int current = 0;
            private ArrayList<Map.Entry<String, ArrayList<Unit>>> entries = new ArrayList<>();


            @Override
            public void first() {
                for (Map.Entry<String, ArrayList<Unit>> entry : map.entrySet()){
                    entries.add(entry);
                }
                current = 0;
            }

            @Override
            public void next() {
                current += 1;
                current %= map.size();
            }

            @Override
            public void prev() {
                current -= 1;
                if (current < 0){
                    current = map.size()-1;
                }
            }

            @Override
            public Map.Entry<String, ArrayList<Unit>> current() {
                if(entries.isEmpty()){
                    return null;
                }
                return entries.get(current);
            }
        };
    }

    public Iterator2<Unit> makeIterator(){
        return new Iterator2<Unit>() {
            private int current;
            private TypeIterator2<Map.Entry<String, ArrayList<Unit>>> entryIter;
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
                entryIter = makeTypeIterator(map);
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
                entryIter.next();
                currentUnitList = entryIter.current().getValue();
            }

            @Override
            public void prevType() {
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
