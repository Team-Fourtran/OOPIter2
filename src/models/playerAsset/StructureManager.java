package src.models.playerAsset;

import java.util.*;
/* Management class for a Player's structures
   Passes commands to specific structures
 */

public class StructureManager extends Manager{
    public ArrayList<Structure> structureList;
    final int maxStructures = 10;
    static ArrayList<String> structureIDs = new ArrayList<>();


    public StructureManager(){
        structureList = new ArrayList<>();
        for (int i = 1; i <= 20; i++)
            structureIDs.add("s" + i);
    }

    //return amount of structures a Player has
    public int getStructureCount(){
        return structureList.size();
    }

    //add a new structure to the map on an Army's location
    public Structure createStructure(String location){
        Structure s = new Structure();
        s.setLocation(location);
        s.setID(structureIDs.get(0));
        structureList.add(s);
        structureIDs.remove(0);
        return s;
    }

    //destroy a structure
    public void decommission(String structureID){
        for (Structure s: structureList){
            if (s.getID().equals(structureID)){
                structureIDs.add(s.getID());
                structureList.remove(s);
            }
        }
    }

    //recycle a structure's ID after it is done using it
    public void freeID(String assetID) {
        int escapee = Integer.parseInt(assetID.substring(assetID.lastIndexOf("u") + 1).trim());
        for (int i = 0; i < structureIDs.size(); i++) {
            String currentID = structureIDs.get(i);
            int id = Integer.parseInt(currentID.substring(currentID.lastIndexOf("u") + 1).trim());
            if (escapee < id) {
                structureIDs.add(i, assetID);
                break;
            }
        }
    }

    //get the location of a specific structure
    public String getPosition(String assetID){
        for (Structure s: structureList)
            if (s.getID().equals(assetID))
                return s.getLocation();
        return null;
    }

    //check to see if a certain structure exists
    public boolean structureExists(String structureID){
        for (Structure s: structureList)
            if (s.getID().equals(structureID))
                return true;
        return false;
    }

    //add command into specific structure's queue
    public void addCommand(Command c, String structureID){
        for (Structure s: structureList)
            if (s.getID().equals(structureID))
                s.addCommand(c);
    }


}