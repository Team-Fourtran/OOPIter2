package models.playerAsset.Assets;


import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Technology.Technology;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.TypeIterator2;
import models.playerAsset.Iterators.specificTypeIterator;
import models.visitor.PlayerVisitor;
import models.visitor.TypeListVisitor;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


/* Management class for a Player's structures
   Passes commands to specific structures
 */

public class StructureManager implements Manager {
    private CopyOnWriteArrayList<Structure> structureList;
    final int maxStructures = 10;
    static ArrayList<String> structureIDs = new ArrayList<>();
    final private StructureFactory factory;
    HashMap<String, ArrayList<Technology>> techMap;


    public StructureManager() {
        structureList = new CopyOnWriteArrayList<>();
        factory = new StructureFactory();
//        structureIterators = new ArrayList<>();
//        structureIterators.add(makeIterator(baseList));
        for (int i = 1; i <= 20; i++)
            structureIDs.add("s" + i);
        techMap = new HashMap<>();
        initMap();

    }

    public void initMap(){
        techMap.put("capital", new ArrayList<>());
        techMap.put("farm", new ArrayList<>());
        techMap.put("mine", new ArrayList<>());
        techMap.put("powerplant", new ArrayList<>());
        techMap.put("fort", new ArrayList<>());
        techMap.put("observationtower", new ArrayList<>());
        techMap.put("university", new ArrayList<>());
        techMap.put("worker", new ArrayList<>());
    }

    //return amount of structures a Player has
    public int getStructureCount() {
        return structureList.size();
    }

    //add a new structure to the map on an Army's location
    // need base tile to configure the structure's work radius
    // this is here cause map shouldn't know if it needs to configure it (Resource vs nonResource structure)
    public Structure createStructure(String type, TileAssociation baseTile) {
        Structure s = factory.makeStructure(type, baseTile);
        s.setID(structureIDs.get(0));
        structureIDs.remove(0);
        structureList.add(s);
        applyTech(s);
        return s;
    }

    public void addTech(String type, Technology tech){
        if (techMap.containsKey(type)) {
            techMap.get(type).add(tech);
            applyTech(type, tech);
        }
    }

    //apply tech to pertinent units upon discovery
    public void applyTech(String structureType, Technology tech){
        if (techMap.containsKey(structureType))
            if (structureType.equals("worker")) {
                for (Structure s : structureList)
                    if (s instanceof ResourceStructure)
                        tech.apply(s);
            }
            else
                for (Structure s: structureList)
                    if (structureType.equals( s.getType()))
                        tech.apply(s);
    }

    //apply existing tech to new unit
    public void applyTech(Structure s){
        for (Technology tech: techMap.get(s.getType()))
            tech.apply(s);
        if (s instanceof ResourceStructure)
            for (Technology tech: techMap.get("worker"))
                tech.apply(s);
    }

    //destroy a structure
    public void removeStructure(PlayerAsset structure) {
        if (structureList.remove(structure)){
            freeID(structure.getID());
            //TODO check if this works
        }
    }

    public int upkeep(int metal) {
        int remainder = metal;
        for (Structure s : structureList)
            if (remainder - s.getUpkeep() < 0) {
                s.setCurrentHealth(s.getCurrentHealth() - 20);
                remainder = 0;
            }
            else
                remainder -= s.getUpkeep();

        return remainder;
    }

    //recycle a structure's ID after it is done using it
    @Override
    public void freeID(String assetID) {
        int escapee = Integer.parseInt(assetID.substring(assetID.lastIndexOf("s") + 1).trim());
        for (int i = 0; i < structureIDs.size(); i++) {
            String currentID = structureIDs.get(i);
            int id = Integer.parseInt(currentID.substring(currentID.lastIndexOf("s") + 1).trim());
            if (escapee < id) {
                structureIDs.add(i, assetID);
                break;
            }
        }
    }

    public void resetCommands() {
        java.util.Iterator<Structure> iter = structureList.iterator();
        iter.forEachRemaining(Structure::resetCommands);
    }

    //go through the structures and, if possible, execute a command
    //used at beginning of player's turn
    public void executeCommands() {
        java.util.Iterator<Structure> iter = structureList.iterator();
        iter.forEachRemaining(Structure::executeCommand);
    }

    //Should be in abstract class
    public void accept(PlayerVisitor v) {
        v.visitStructureManager(this);
    }

    public Iterator2<Structure> makeIterator(){
        return new Iterator2<Structure>() {
            private int current;
            private specificTypeIterator<Structure> entryIter;
            private ArrayList<Structure> currentStructureList;
            private Map<String, ArrayList<Structure>> map = new HashMap<>();

            @Override
            public Iterator2<Structure> first() {
                TypeListVisitor vis = new TypeListVisitor();
                for (Structure u : structureList){
                    u.accept(
                            vis
                    );
                }
                map = vis.getStructureMap();
                entryIter = new specificTypeIterator<>(map);
                entryIter.first();
                if (entryIter.current() == null){
                    currentStructureList = null;
                }
                else{
                    currentStructureList = entryIter.current().getValue();
                }
                current = 0;
                return this;
            }

            @Override
            public void next() {
                current += 1;
                current %= currentStructureList.size();
            }

            @Override
            public void prev() {
                current -= 1;
                if (current < 0){
                    current = currentStructureList.size()-1;
                }
            }

            @Override
            public Structure current() {
                if (currentStructureList == null){
                    return null;
                }
                return currentStructureList.get(current);
            }

            @Override
            public void nextType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.next();
                currentStructureList = entryIter.current().getValue();
            }

            @Override
            public void prevType() {
                current = 0;    //Reset since the next type may have less instances
                entryIter.prev();
                currentStructureList = entryIter.current().getValue();
            }

            @Override
            public String getElement() {
                return entryIter.current().getKey();
            }
        };
    }

    public int getWheatCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getHarvestCount("food");
            }
        return sum;
    }

    public int getFoodCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getProductionCount("food");
            }
        return sum;
    }

    public int getOreCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getHarvestCount("ore");
            }
        return sum;
    }

    public int getMetalCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getProductionCount("ore");
            }
        return sum;
    }

    public int getEnergyCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getHarvestCount("energy");
            }
        return sum;
    }

    public int getScienceCount(){
        ResourceStructure rs;
        int sum = 0;
        for (Structure s: structureList)
            if (s instanceof ResourceStructure) {
                rs = (ResourceStructure) s;
                sum += rs.getProductionCount("food");
            }
        return sum;
    }

    public HashMap<String, ArrayList<Technology>> getTech(){
        return techMap;
    }
}
