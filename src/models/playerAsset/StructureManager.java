package models.playerAsset;

import models.command.Command;
import models.visitor.PlayerVisitor;
import java.util.ArrayList;

/* Management class for a Player's structures
   Passes commands to specific structures
 */

public class StructureManager implements Manager {
    public ArrayList<Structure> structureList;
    ArrayList<PlayerAsset> baseList;
    final int maxStructures = 10;
    static ArrayList<String> structureIDs = new ArrayList<>();
    final private StructureFactory factory;
    ArrayList<Iterator<PlayerAsset>> structureIterators;

    public StructureManager() {
        factory = new StructureFactory();
        structureList = new ArrayList<>();
        baseList = new ArrayList<>();
        structureIterators = new ArrayList<>();
        structureIterators.add(makeIterator(baseList));
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
        addStructureToList(s, type);
        return s;
    }

    public void addStructureToList(Structure s, String type) {

        switch (type) {
//            case "base":
//                structureList.add(s);
//                baseList.add((Base) s);
//                break;
                /*
            case "farm":
                structureList.add(s);
                baseList.add((Farm)s);
                break;
            case "mine":
                structureList.add(s);
                baseList.add((Mine)s);
                break;
            case "power plant":
                structureList.add(s);
                baseList.add((PowerPlant)s);
                break;
            case "fort":
                structureList.add(s);
                baseList.add((Fort)s);
                break;
            case "observation tower":
                structureList.add(s);
                baseList.add((ObservationTower)s);
                break;
            case "university":
                structureList.add(s);
                baseList.add((University)s);
                break;
             */
        }
    }

    //destroy a structure
    public void decommission(String structureID) {
        for (Structure s : structureList) {
            if (s.getID().equals(structureID)) {
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

    public void resetCommands() {
        for (Structure s : structureList) {
            s.resetCommands();
        }
    }

    //go through the structures and, if possible, execute a command
    //used at beginning of player's turn
    public void executeCommands() {
        for (Structure s : structureList) {
            s.executeCommand();
        }
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
                System.out.println(list.get(index).getType());
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

        };
    }

    /*test method to grab iterator*/
    public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> getTypeIterator() {
        return makeTypeIterator(structureIterators);
    }
}
