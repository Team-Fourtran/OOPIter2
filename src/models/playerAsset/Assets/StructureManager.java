package models.playerAsset.Assets;


import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.TypeIterator;
import models.visitor.PlayerVisitor;
import models.visitor.TypeListVisitor;

import java.util.ArrayList;
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
    public Structure createStructure(String type) {
        Structure s = factory.makeStructure(type);
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
    public void removeStructure(Structure structure) {
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

/*    public Iterator<PlayerAsset> makeIterator(ArrayList<PlayerAsset> list) {
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

    *//*test method to grab iterator*//*
    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> getTypeIterator() {
        return makeTypeIterator(structureIterators);
    }*/
public Iterator2<Structure> makeIterator(){
    return new Iterator2<Structure>() {
        private int current;
        private int typeIndex;
        private int typeSize;
        private ArrayList<Structure> currentType;
        private ArrayList<ArrayList<Structure>> typeList;

        private int getSize(){
            return currentType.size();
        }

        @Override
        public void first() {
            typeList = new ArrayList<>();
            for (Structure s : structureList){
                s.accept(
                        new TypeListVisitor()
                );
            }
            typeIndex = 0;
            typeSize = typeList.size();
            currentType = typeList.get(typeIndex);
            for (int i = 0; i < typeSize; i++){
                if (currentType.size() == 0){
                    nextType();
                }
            }
            current = 0;
        }

        @Override
        public void next() {
            current += 1;
            current %= getSize();
        }

        @Override
        public void prev() {
            current -= 1;
            if (current < 0){
                current = getSize()-1;
            }
        }

        @Override
        public Structure current() {
            if (getSize() > 0){
                return currentType.get(current);
            }
            return null;
        }

        @Override
        public void nextType() {
            typeIndex++;
            typeIndex %= typeSize;
            currentType = typeList.get(typeIndex);
            if (currentType.size() == 0){
                nextType();
            }
        }

        @Override
        public void prevType() {
            typeIndex--;
            if (typeIndex < 0){
                typeIndex = typeSize-1;
            }
            currentType = typeList.get(typeIndex);
            if (currentType.size() == 0){
                prevType();
            }
        }

        @Override
        public String getElement() {
            return currentType.getClass().getFields().toString();
        }
    };
}
}
