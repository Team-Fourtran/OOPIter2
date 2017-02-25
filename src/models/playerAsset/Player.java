package models.playerAsset;

import models.visitor.PlayerVisitor;

import java.util.ArrayList;

public class Player {
    //public for debugging
    public final ArmyManager armies;
    public final UnitManager units;
    public final StructureManager structures;

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
    public AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> makeAssetIterator(ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list) {
        return new AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>>(){

            private int index = 0;
            private TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current = current();

            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> first() {
                return list.get(0);
            }

            public void nextMode(){
                index = (index + 1) % list.size();
                current = list.get(index);
            }

            public void prevMode(){
                if (index != 0)
                    index--;
                else
                    index = list.size() - 1;
                current = list.get(index);
            }

            public void nextType() {
                current.nextType();
            }

            public void prevType() {
                current.prevType();
            }

            public void next() {
                current.next();
            }

            public void prev() {
                current.prev();
            }

            public TypeIterator<PlayerAsset, Iterator<PlayerAsset>> current() {
                return list.get(index);
            }

            public PlayerAsset getElement(){
                return current().getElement();
            }
        };
    }

    public AssetIterator<PlayerAsset, TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> getAssetIterator(){
        ArrayList<TypeIterator<PlayerAsset, Iterator<PlayerAsset>>> list = new ArrayList<>();
        list.add(units.getTypeIterator());
        list.add(structures.getTypeIterator());
        //list.add(armies.getTypeIterator());
        return makeAssetIterator(list);
    }
}