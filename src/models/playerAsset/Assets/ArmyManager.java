package models.playerAsset.Assets;

import models.playerAsset.Assets.Units.Unit;
import models.visitor.PlayerVisitor;

import java.util.ArrayList;

/* Management class for a Player's armies. Keeps references to
   all armies and passes commands to specific ones.
 */
public class ArmyManager{
    private ArrayList<RallyPoint> rallyPointList = new ArrayList<>();
    private ArrayList<Army> armyList = new ArrayList<>();
    private final int maxArmies = 10;
    private static ArrayList<String> armyIDs = new ArrayList<>();

    public ArmyManager(){
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


    //free an army's ID when they are done using it for recycling
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
    public int calculateTotalUpkeep(){
        int totalUpkeep = 0;
        return totalUpkeep;
    }

    //Go through all of the armies and, if possible, execute a command
    public void executeCommands(){
        for (Army a: armyList) {
            a.executeCommand();
        }
    }

    //reset all of the armies' abilities to execute commands
    public void resetCommands(){
        for (Army a: armyList) {
            a.resetCommands();
        }
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
}