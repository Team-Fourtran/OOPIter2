package models.playerAssetNew;


import models.command.Command;
import java.util.ArrayList;

public class Player {
    
    private ArrayList<Command> playerQueue;
    private final ArmyManager armies;
    private final UnitManager units;
    private final StructureManager structures;


    public Player(){
        playerQueue = new ArrayList<>(0);
        armies = new ArmyManager();
        units = new UnitManager();
        structures = new StructureManager();
    }

    //recieve command from command generator
    public void notify(Command command){
        System.out.println("Received command");
        playerQueue.add(command);
    }

    //method to do maintenance tasks on player's assets
    public void beginTurn(){
        playerQueue.forEach(Command::execute);
    }

    //do end of turn housekeeping like resetting commands
    public void endTurn(){
        armies.resetCommands();
        structures.resetCommands();
        units.resetCommands();
    }
}
