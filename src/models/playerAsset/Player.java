package models.playerAsset;

import models.visitor.PlayerVisitor;

import java.util.ArrayList;

public class Player {
    //public for debugging
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
        v.visitPlayer(this);
    }
    public AssetIterator<TypeIterator<Iterator<PlayerAsset>>> makeAssetIterator(ArrayList<TypeIterator<Iterator<PlayerAsset>>> list) {
        return new AssetIterator<TypeIterator<Iterator<PlayerAsset>>>(){

            private int index = 0;
            private TypeIterator<Iterator<PlayerAsset>> current = current();

            public TypeIterator<Iterator<PlayerAsset>> first() {
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

            public TypeIterator<Iterator<PlayerAsset>> current() {
                return list.get(index);
            }
        };
    }

    public UnitManager getUnits() {
        return units;
    }

    public ArmyManager getArmies() {
        return armies;
    }

    public StructureManager getStructures() {
        return structures;
    }
}