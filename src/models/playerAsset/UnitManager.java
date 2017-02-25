package models.playerAsset;

import models.visitor.PlayerVisitor;

import java.util.ArrayList;

/* Manager for a Player's Units
   Helps create units and pass commands to them
 */
public class UnitManager {
    
    ArrayList<Unit> unitList;
    int unitCount;
    final private UnitFactory factory;
    final int maxUnits = 25;
    final int maxUnitType = 10;
    static ArrayList<String> unitIDs = new ArrayList<String>();

    public UnitManager(){
        this.factory = new UnitFactory();
        unitList = new ArrayList<>();
        unitCount = 0;
        for (int i = 1; i <= 50; i++)
            unitIDs.add("u" + i);
    }

    //add a new unit to the map on the structure's location that created it
    public Unit addNewUnit(String type){
        Unit newUnit = factory.makeUnit(type);
        newUnit.setID(unitIDs.get(0));
        unitIDs.remove(0);
        unitList.add(newUnit);
        unitCount++;
        return newUnit;
    }

    //method to add units from disbanded army into the unit list
    public void addUnits(ArrayList<Unit> units){
        unitList.addAll(units);
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

    //if possible, execute a (movement) command in units
    public void executeCommands(){
        for (Unit u: unitList){
            u.executeCommand();
        }
    }

    //reset the ability for a unit to move
    public void resetCommands(){
        for (Unit u: unitList) {
            u.resetCommands();
        }
    }

    //Should be in abstract class
    public void accept(PlayerVisitor v){
        v.visitUnitManager(this);
    }
}
