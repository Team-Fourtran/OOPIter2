package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Colonist;
import models.playerAsset.Player;
import models.playerAsset.Unit;

public class CTRLCreateStructureCommand implements Command{
    private GameMap map;
    private Player player;
    private Unit unit;
    private String structureType;

    public CTRLCreateStructureCommand(GameMap map, Player p, Unit unit, String structureType){
        this.map = map;
        this.player = p;
        this.unit = unit;
        this.structureType = structureType;
    }

    @Override
    public void execute() {
        if(structureType.equals("capital")){
            if(unit instanceof Colonist){
                new CreateCapitalCommand(map, player, unit).execute();
            }
            else{
                System.out.println("Need Colonist to create capital!");
            }
        }
    }

    @Override
    public double getTurns() {
        //TODO: Based off number of workers and structure type
        return 0;
    }
}
