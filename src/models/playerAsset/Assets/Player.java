package models.playerAsset.Assets;


import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.Iterator2;
import models.visitor.PlayerVisitor;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private final String playerName;
    private final ArmyManager armies;
    private final UnitManager units;
    private final StructureManager structures;

    public Player(String playerName){
        this.playerName = playerName;
        armies = new ArmyManager();
        units = new UnitManager();
        structures = new StructureManager();
    }

    public String getName(){
        return playerName;
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

    public UnitManager getUnits() {
        return units;
    }

    public ArmyManager getArmies() {
        return armies;
    }

    public StructureManager getStructures() {
        return structures;
    }

    public AssetIterator<PlayerAsset> makeIterator(){
        return new AssetIterator<PlayerAsset>() {
            private String[] modes = {"ARMY MODE", "UNIT MODE", "STRUCTURE MODE", "RALLY POINT MODE"};
            private Iterator2<? extends PlayerAsset> currentIter;
            private int currentIndex;
            private Map<String, Iterator2<? extends PlayerAsset>> map = new HashMap<>();

            private void findPrevState(String prevMode, String prevType, PlayerAsset prevAsset){
                String firstMode = getCurrentMode();
                while(!getCurrentMode().equals(prevMode)){
                    next();
                    if(getCurrentMode().equals(firstMode)){
                        return;
                    }
                }
                String firstType = getElement();
                while(!getElement().equals(prevType)){
                    nextType();
                    if (getElement().equals(firstType)){
                        return;
                    }
                }
                PlayerAsset firstAsset = current();
                while(current() != prevAsset){
                    nextInstance();
                    if(current() == firstAsset){
                        return;
                    }
                }
            }

            @Override
            public void update() {
                String prevMode = getCurrentMode();
                String prevType = getElement();
                PlayerAsset prevAsset = current();
                first();
                findPrevState(prevMode, prevType, prevAsset);
            }

            @Override
            public AssetIterator<PlayerAsset> first() {
                currentIndex = 0;
                map.put("ARMY MODE", armies.makeIterator().first());
                map.put("UNIT MODE", units.makeIterator().first());
                map.put("STRUCTURE MODE", structures.makeIterator().first());
                map.put("RALLY POINT MODE", armies.makeRPIterator().first());

                for (int i = 0; i < map.size(); i++){
                    currentIter = map.get(modes[currentIndex]);
                    currentIter.first();
                    if (currentIter.current() == null || currentIter==null){
                        next();
                    }
                }
                return this;
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