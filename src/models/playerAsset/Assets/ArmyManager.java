package models.playerAsset.Assets;

import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.specificTypeIterator;
import models.visitor.PlayerVisitor;
import models.visitor.TypeListVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* Management class for a Player's armies. Keeps references to
   all armies and passes commands to specific ones.
 */
public class ArmyManager implements Manager {
    private ArrayList<RallyPoint> rallyPointList = new ArrayList<>();
    private CopyOnWriteArrayList<Army> armyList;
    private final int maxArmies = 10;
    private static ArrayList<String> armyIDs = new ArrayList<>();

    public ArmyManager(){
        armyList = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 20; i++)
            armyIDs.add("a" + i);
    }

    //create a new army with given units and add it to the armyList
    public Army formArmy(Unit... units){
        Army newArmy = new Army(units);
        newArmy.setID(armyIDs.remove(0));
        armyList.add(newArmy);
        return newArmy;
    }

    public RallyPoint formRallyPoint(Army army){
        RallyPoint rallyPoint = new RallyPoint();
        rallyPoint.setArmy(army);
        rallyPointList.add(rallyPoint);
        return rallyPoint;
    }

    public void removeArmy(PlayerAsset asset){
        RallyPoint toRemove = null;
        for (RallyPoint rp : rallyPointList){
            if (rp.getArmy() == asset){
                toRemove = rp;
            }
        }
        rallyPointList.remove(toRemove);
        armyList.remove(asset);
    }
    
    public RallyPoint getRallyPoint(Army army){
        for (RallyPoint rp : rallyPointList){
            if (rp.getArmy() == army){
                return rp;
            }
        }
        return null;
    }
    

    //free an army's ID when they are done using it for recycling
    @Override
    public void freeID(String assetID) {
    	int escapee = Integer.parseInt(assetID.substring(assetID.lastIndexOf("u") + 1).trim());
    	for (int i = 0; i < armyIDs.size(); i++) {
    		String currentID = armyIDs.get(i);
    		int id = Integer.parseInt(currentID.substring(currentID.lastIndexOf("u") + 1).trim());
    		if (escapee < id) {
    			armyIDs.add(i, assetID);
    			break;
    		}
    	}
    }

    //calculate total upkeep from each army, unit by unit
    @Override
    public int calculateTotalUpkeep(){
        int totalUpkeep = 0;
        return totalUpkeep;
    }

    //Go through all of the armies and, if possible, execute a command
    public void executeCommands(){
        Iterator<Army> iter = armyList.iterator();
        iter.forEachRemaining(Army::executeCommand);
    }

    //reset all of the armies' abilities to execute commands
    public void resetCommands(){
        Iterator<Army> iter = armyList.iterator();
        iter.forEachRemaining(Army::resetCommands);
    }

    //Should be in abstract class
    public void accept(PlayerVisitor v){
        v.visitArmyManager(this);
    }

    public Army debugGetArmy(){
        return armyList.get(0);
    }

    public RallyPoint debugGetRallyPoint(){
        return rallyPointList.get(0);
    }

    public Iterator2<Army> makeIterator(){
        return new Iterator2<Army>() {
            private int current;
            private specificTypeIterator<Army> entryIter;
            private ArrayList<Army> currentArmyList;
            private Map<String, ArrayList<Army>> map = new HashMap<>();

            @Override
            public Iterator2<Army> first() {
                TypeListVisitor vis = new TypeListVisitor();
                for (Army u : armyList){
                    u.accept(
                            vis
                    );
                }
                map = vis.getArmyMap();
                entryIter = new specificTypeIterator<>(map);
                entryIter.first();
                if (entryIter.current() == null){
                    currentArmyList = null;
                }
                else{
                    currentArmyList = entryIter.current().getValue();
                }
                current = 0;
                return this;
            }

            @Override
            public void next() {
                current += 1;
                current %= currentArmyList.size();
            }

            @Override
            public void prev() {
                current -= 1;
                if (current < 0){
                    current = currentArmyList.size()-1;
                }
            }

            @Override
            public Army current() {
                if (currentArmyList == null){
                    return null;
                }
                return currentArmyList.get(current);
            }

            @Override
            public void nextType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.next();
                currentArmyList = entryIter.current().getValue();
            }

            @Override
            public void prevType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.prev();
                currentArmyList = entryIter.current().getValue();
            }

            @Override
            public String getElement() {
                return entryIter.current().getKey();
            }
        };
    }

    public Iterator2<RallyPoint> makeRPIterator(){
        return new Iterator2<RallyPoint>() {
            private int current;
            private specificTypeIterator<RallyPoint> entryIter;
            private ArrayList<RallyPoint> currentRPList;
            private Map<String, ArrayList<RallyPoint>> map = new HashMap<>();

            @Override
            public Iterator2<RallyPoint> first() {
                TypeListVisitor vis = new TypeListVisitor();
                for (RallyPoint rp : rallyPointList){
                    rp.accept(
                            vis
                    );
                }
                map = vis.getRPMap();
                entryIter = new specificTypeIterator<>(map);
                entryIter.first();
                if (entryIter.current() == null){
                    currentRPList = null;
                }
                else{
                    currentRPList = entryIter.current().getValue();
                }
                current = 0;
                return this;
            }

            @Override
            public void next() {
                current += 1;
                current %= currentRPList.size();
            }

            @Override
            public void prev() {
                current -= 1;
                if (current < 0){
                    current = currentRPList.size()-1;
                }
            }

            @Override
            public RallyPoint current() {
                if (currentRPList == null){
                    return null;
                }
                return currentRPList.get(current);
            }

            @Override
            public void nextType() {
                entryIter.next();
                currentRPList = entryIter.current().getValue();
            }

            @Override
            public void prevType() {
                entryIter.prev();
                currentRPList = entryIter.current().getValue();
            }

            @Override
            public String getElement() {
                return entryIter.current().getKey();
            }
        };
    }
}