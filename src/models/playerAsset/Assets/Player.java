package models.playerAsset.Assets;


import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Iterators.AssetIterator;
import models.playerAsset.Iterators.CommandIterator;
import models.playerAsset.Iterators.Iterator2;
import models.visitor.CommandListVisitor;
import models.visitor.PlayerVisitor;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private final String playerName;
    private final ArmyManager armies;
    private final UnitManager units;
    private final StructureManager structures;
    private int wheat, food, ore, metal, energy, science;

    public Player(String playerName){
        this.playerName = playerName;
        armies = new ArmyManager();
        units = new UnitManager();
        structures = new StructureManager();
        wheat = food = ore = metal = energy = science = 0;
    }

    public String getName(){
        return playerName;
    }

    //method to do maintenance tasks on player's assets
    public void beginTurn(){
        armies.executeCommands();
        structures.executeCommands();
        units.executeCommands();
        getResourceCounts();
        subtractResources();
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

    public void getResourceCounts(){
        wheat = structures.getWheatCount();
        food = structures.getFoodCount();
        ore = structures.getOreCount();
        metal = structures.getMetalCount();
        energy = structures.getEnergyCount();
        science = structures.getScienceCount();
    }

    public void subtractResources(){
        food = units.upkeep(food);
        metal = structures.upkeep(metal);
    }

    public int getWheat(){
        return wheat;
    }

    public int getFood(){
        return food;
    }

    public int getOre(){
        return ore;
    }

    public int getMetal(){
        return metal;
    }

    public int getEnergy(){
        return energy;
    }

    public int getScience(){
        return science;
    }

    public AssetIterator makeIterator(){
        return new AssetIterator() {
            private String[] modes = {"ARMY MODE", "UNIT MODE", "STRUCTURE MODE", "RALLY POINT MODE"};
            private Iterator2<? extends PlayerAsset> currentIter;
            private int currentIndex;
            private Map<String, Iterator2<? extends PlayerAsset>> map = new HashMap<>();
            private CommandIterator cmdIter;

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

            private void updateCommandIter(){
                CommandListVisitor cmdGetter = new CommandListVisitor();
                current().accept(cmdGetter);
                cmdIter = cmdGetter.getIterator();
            }

            @Override
            public void update() {
                String prevMode = getCurrentMode();
                String prevType = getElement();
                PlayerAsset prevAsset = current();
                first();
                findPrevState(prevMode, prevType, prevAsset);
                updateCommandIter();
            }

            @Override
            public AssetIterator first() {
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
                updateCommandIter();
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
                updateCommandIter();
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
                updateCommandIter();
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
                updateCommandIter();
            }

            @Override
            public void prevType() {
                currentIter.prevType();
                updateCommandIter();
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
                updateCommandIter();
            }

            @Override
            public void prevInstance() {
                currentIter.prev();
                updateCommandIter();
            }

            @Override
            public PlayerAsset current() {
                return currentIter.current();
            }
            /////////////INSTANCE///////////////

            /////////////COMMANDS///////////////
            @Override
            public void nextCommand() {
                cmdIter.next();
            }

            @Override
            public void prevCommand() {
                cmdIter.prev();
            }

            @Override
            public CTRLCommand getCommand() {
                return cmdIter.current();
            }
            /////////////COMMANDS///////////////
        };
    }
}