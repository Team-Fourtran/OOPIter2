package models.playerAsset.Assets;


import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.Iterator;
import models.playerAsset.Iterators.Iterator2;
import models.playerAsset.Iterators.TypeIterator;

import models.visitor.PlayerVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private final ArmyManager armies;
    private final UnitManager units;
    private final StructureManager structures;

    public Player(){
        armies = new ArmyManager();
        units = new UnitManager();
        structures = new StructureManager();
    }

    //method to do maintenance tasks on player's assets
    public void beginTurn(){
        armies.executeCommands();
        structures.executeCommands();
        units.executeCommands();
    }

    //do end of turn housekeeping like resetting commands
    public void endTurn(){
        armies.resetCommands();
        structures.resetCommands();
        units.resetCommands();
    }

    public void accept(PlayerVisitor v){
        armies.accept(v);
        units.accept(v);
        structures.accept(v);
    }

//    private AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> makeAssetIterator(ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list) {
//        return new AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>>(){
//
//            private int index = 0;
//            private TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current = current();
//
//            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> first() {
//                return list.get(0);
//            }
//
//            public void nextMode(){
//                index = (index + 1) % list.size();
//                current = list.get(index);
//            }
//
//            public void prevMode(){
//                if (index != 0)
//                    index--;
//                else
//                    index = list.size() - 1;
//                current = list.get(index);
//            }
//
//            public void nextType() {
//                current.nextType();
//            }
//
//            public void prevType() {
//                current.prevType();
//            }
//
//            public void next() {
//                current.next();
//            }
//
//            public void prev() {
//                current.prev();
//            }
//
//            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current() {
//                return list.get(index);
//            }
//
//
//            public PlayerAsset getElement(){
//                return current().getElement();
//            }
//
//            @Override
//            public String getCurrentMode() {
//                if (getElement() instanceof Unit)
//                    return "Unit Mode";
//                else if (getElement() instanceof Structure)
//                    return "Structure Mode";
//                else
//                    return "Army Mode";
//            }
//
//            public String getCurrentType(){
//                return current.getCurrentType();
//            }
//
//        };
//    }

    public UnitManager getUnits() {
        return units;
    }

    public ArmyManager getArmies() {
        return armies;
    }

    public StructureManager getStructures() {
        return structures;
    }

//    public AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> getAssetIterator(){
//        ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list = new ArrayList<>();
//        //list.add(units.getTypeIterator());
////        list.add(structures.getTypeIterator());
//        //list.add(armies.getTypeIterator());
//        return makeAssetIterator(list);
//    }

    public AssetIterator<PlayerAsset> makeIterator(){
        return new AssetIterator<PlayerAsset>() {
            private String[] modes = {"ARMY MODE", "UNIT MODE", "STRUCTURE MODE"};
            private Iterator2<? extends PlayerAsset> currentIter;
            private int currentIndex;
            private Map<String, Iterator2<? extends PlayerAsset>> map = new HashMap<>();

            @Override
            public void first() {
                currentIndex = 0;
                map.put("ARMY MODE", armies.makeIterator());
                map.put("UNIT MODE", units.makeIterator());
                map.put("STRUCTURE MODE", structures.makeIterator());
                //map.put("RALLY POINT MODE", armies.makeRPIterator());
                for (int i = 0; i < map.size(); i++){
                    currentIter = map.get(modes[currentIndex]);
                    currentIter.first();
                    if (currentIter.current() == null){
                        next();
                    }
                }
            }

            ////////////////MODE////////////////
            @Override
            public void next() {
                currentIndex += 1;
                currentIndex %= map.size();
                for (int i = 0; i < map.size(); i++){
                    currentIter = map.get(modes[currentIndex]);
                    currentIter.first();
                    if (currentIter.current() == null){
                        next();
                    }
                }
            }

            @Override
            public void prev() {
                currentIndex -= 1;
                if (currentIndex < 0){
                    currentIndex = map.size()-1;
                }
                currentIter = map.get(modes[currentIndex]);
                currentIter.first();
                if (currentIter.current() == null){
                    prev();
                }
            }

            @Override
            public String getCurrentMode() {
                return modes[currentIndex];
            }
            ////////////////MODE////////////////

            ///////////////TYPE////////////////
            @Override
            public void nextType() {
                currentIter.nextType();
            }

            @Override
            public void prevType() {
                currentIter.prevType();
            }

            //Gets current Type
            @Override
            public String getElement() {
                return currentIter.getElement();
            }
            ///////////////TYPE////////////////

            /////////////INSTANCE///////////////
            @Override
            public void nextInstance() {
                currentIter.next();
            }

            @Override
            public void prevInstance() {
                currentIter.prev();
            }

            @Override
            public PlayerAsset current() {
                return currentIter.current();
            }
            /////////////INSTANCE///////////////
        };
    }
}