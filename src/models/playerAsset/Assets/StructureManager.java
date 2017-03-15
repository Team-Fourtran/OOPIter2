package models.playerAsset.Assets;


import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Structures.*;
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
    ArrayList<PlayerAsset> baseList;
    final int maxStructures = 10;
    static ArrayList<String> structureIDs = new ArrayList<>();
    final private StructureFactory factory;

    public StructureManager() {
        structureList = new CopyOnWriteArrayList<>();
        factory = new StructureFactory();
        baseList = new ArrayList<>();
//        structureIterators = new ArrayList<>();
//        structureIterators.add(makeIterator(baseList));
        for (int i = 1; i <= 20; i++)
            structureIDs.add("s" + i);
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
        //TODO: fix addStructureToList
        structureList.add(s);
        addStructureToList(s, type);
        //TODO END
        return s;
    }

    public void addStructureToList(Structure s, String type) {
        switch (type) {
            case "base":
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
            case "power plant":
                structureList.add(s);
                baseList.add((PowerPlant) s);
                break;
            case "fort":
                structureList.add(s);
                baseList.add((Fort) s);
                break;
            case "observation tower":
                structureList.add(s);
                baseList.add((ObservationTower) s);
                break;
            case "university":
                structureList.add(s);
                baseList.add((University) s);
                break;
        }
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
}
