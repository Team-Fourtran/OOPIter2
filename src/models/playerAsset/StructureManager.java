package models.playerAsset;

import models.visitor.PlayerVisitor;
import java.util.ArrayList;

/* Management class for a Player's structures
   Passes commands to specific structures
 */

public class StructureManager implements Manager{
    public ArrayList<Structure> structureList;
    final int maxStructures = 10;
    final private StructureFactory factory;
    static ArrayList<String> structureIDs = new ArrayList<>();
    //TODO: Structure Factory


    public StructureManager(){
        structureList = new ArrayList<>();
        factory = new StructureFactory();
        for (int i = 1; i <= 20; i++)
            structureIDs.add("s" + i);
    }

    //return amount of structures a Player has
    public int getStructureCount(){
        return structureList.size();
    }

    public Structure createStructure(String type){
        Structure newStructure = factory.makeStructure(type);
        newStructure.setID(structureIDs.get(0));
        structureIDs.remove(0);
        structureList.add(newStructure);
        return newStructure;
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

    @Override
    public int calculateTotalUpkeep() {
        return 0;
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

    public void resetCommands(){
        for (Structure s: structureList){
            s.resetCommands();
        }
    }

    //go through the structures and, if possible, execute a command
    //used at beginning of player's turn
    public void executeCommands(){
        for (Structure s: structureList) {
            s.executeCommand();
        }
    }

    //Should be in abstract class
    public void accept(PlayerVisitor v){
        v.visitStructureManager(this);
    }
}
