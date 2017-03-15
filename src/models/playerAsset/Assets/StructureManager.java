package models.playerAsset.Assets;


import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Technology.Technology;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.TypeIterator;
import models.visitor.PlayerVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/* Management class for a Player's structures
   Passes commands to specific structures
 */

public class StructureManager implements Manager {
    private CopyOnWriteArrayList<Structure> structureList;
    ArrayList<PlayerAsset> baseList;
    final int maxStructures = 10;
    static ArrayList<String> structureIDs = new ArrayList<>();
    final private StructureFactory factory;
    ArrayList<Iterator<PlayerAsset>> structureIterators;
    HashMap<String, ArrayList<Technology>> techMap;

    public StructureManager() {
        structureList = new CopyOnWriteArrayList<>();
        factory = new StructureFactory();
        baseList = new ArrayList<>();
        structureIterators = new ArrayList<>();
        structureIterators.add(makeIterator(baseList));
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
    public Structure createStructure(String type) {
        Structure s = factory.makeStructure(type);
        s.setID(structureIDs.get(0));
        structureIDs.remove(0);
        addStructureToList(s, type);
        applyTech(s);
        return s;
    }

    public void addStructureToList(Structure s, String type) {
        switch (type) {
            case "capital":
                structureList.add(s);
                baseList.add((Capital) s);
                break;

            case "farm":
                structureList.add(s);
                baseList.add((Farm) s);
                break;
            case "mine":
                structureList.add(s);
                baseList.add((Mine) s);
                break;
            case "powerplant":
                structureList.add(s);
                baseList.add((PowerPlant) s);
                break;
            case "fort":
                structureList.add(s);
                baseList.add((Fort) s);
                break;
            case "observationtower":
                structureList.add(s);
                baseList.add((ObservationTower) s);
                break;
            case "university":
                structureList.add(s);
                baseList.add((University) s);
                break;
        }
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

    @Override
    public int calculateTotalUpkeep() {
        return 0;
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

    public Iterator<PlayerAsset> makeIterator(ArrayList<PlayerAsset> list) {
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
                index = (index + 1) % list.size();
            }

            @Override
            public void prev() {
                if (index != 0)
                    index--;
                else
                    index = list.size() - 1;
            }

            public PlayerAsset current() {
                //System.out.println(list.get(index).getType());
                return list.get(index);
            }

            public PlayerAsset getElement() {
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

            public PlayerAsset getElement() {
                return current.current();
            }

            public String getCurrentType(){
                return getElement().getType();
            }

        };
    }

    /*test method to grab iterator*/
    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> getTypeIterator() {
        return makeTypeIterator(structureIterators);
    }
}
