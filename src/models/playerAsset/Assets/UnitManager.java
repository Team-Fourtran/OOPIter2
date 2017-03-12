
package models.playerAsset.Assets;
import models.playerAsset.Assets.Units.*;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.TypeIterator;
import models.visitor.PlayerVisitor;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/* Manager for a Player's Units
   Helps create units and pass commands to them
 */
public class UnitManager {
    private CopyOnWriteArrayList<Unit> unitList;
    final private UnitFactory factory;
    ArrayList<PlayerAsset> explorerList;
    ArrayList<PlayerAsset> colonistList;
    ArrayList<PlayerAsset> meleeList;
    ArrayList<PlayerAsset> rangedList;
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
        unitIterators = new ArrayList<>();
        unitIterators.add(makeIterator(explorerList));
        unitIterators.add(makeIterator(colonistList));
        unitIterators.add(makeIterator(meleeList));
        unitIterators.add(makeIterator(rangedList));
    }

    public void removeUnit(Unit unit){
        unitList.remove(unit);
    }

    /*test method to grab iterator*/
    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> getTypeIterator(){
        return makeTypeIterator(unitIterators);
    }

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

    public Iterator<PlayerAsset> makeIterator(ArrayList<PlayerAsset> list){
        return new Iterator<PlayerAsset>() {

            private int index = 0;

            @Override
            public PlayerAsset first() {
                if (!list.isEmpty())
                    return list.get(0);
                return null;
            }

            @Override
            public void next() {
                index = (index+1) % list.size();
            }

            @Override
            public void prev() {
                if (index != 0)
                    index--;
                else
                    index = list.size()-1;
            }

            public PlayerAsset current(){
                //System.out.println(list.get(index).getType());
                return list.get(index);
            }

            public PlayerAsset getElement(){
                return list.get(index);
            }
        };
    }

    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> makeTypeIterator(ArrayList<Iterator<PlayerAsset>> list) {
        return new TypeIterator<PlayerAsset, Iterator<PlayerAsset>>() {

            private int index = 0;
            private Iterator<PlayerAsset> current = current();

            @Override
            public Iterator<PlayerAsset> first() {
                return list.get(0);
            }

            public void nextType() {
                index = (index + 1) % list.size();
                current = list.get(index);
            }

            public void prevType() {
                if (index != 0)
                    index--;
                else
                    index = list.size() - 1;
                current = list.get(index);
            }

            public void next() {
                current.next();
            }

            public void prev() {
                current.prev();
            }

            public Iterator<PlayerAsset> current() {
                return list.get(index);
            }

            public PlayerAsset getElement(){
                return current.current();
            }

            public String getCurrentType(){
                return getElement().getType();
            }

        };
    }
}
