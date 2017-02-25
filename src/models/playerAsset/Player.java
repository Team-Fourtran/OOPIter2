package models.playerAsset;

import models.visitor.PlayerVisitor;

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
}
