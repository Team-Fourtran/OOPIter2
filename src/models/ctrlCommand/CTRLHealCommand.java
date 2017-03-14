package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.HealCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLHealCommand implements CTRLCommand{
    private Structure structure;
    private TileAssociation tile;
    private CommandComponents parts;

    private boolean isConfigured;

    public CTRLHealCommand(){
        isConfigured = false;
    }

    public void configure(Structure structure, TileAssociation tile){
        this.isConfigured = true;
        this.tile = tile;
        this.structure = structure;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        System.out.println(parts + "\n" + tile);
        this.tile = parts.getDestinationTile(); //Query parts for the destination tile.
        System.out.println(parts + "\n" + tile);
        if(null != tile){       //Calling requestDestinationTile set it to null before initiating the highlighting
            //If it's not null, highlighting worked properly and we have a DestinationTile
            isConfigured = true;    //Flip the flag so that it'll execute properly without exceptions
            parts.requestExecution();   //Request execution
        } else {
            throw new CommandNotConfiguredException("queryAgain() was called, but the DestinationTile is null");
        }
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.structure = (Structure) parts.getRequestingAsset();
        parts.requestDestinationTile(this);
        isConfigured = false;
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            structure.addCommand(
                    new HealCommand(structure, tile)
            );
        } else  throw new CommandNotConfiguredException("[" + this + "] is not configured.");
    }

    @Override
    public String toString(){
        return "Heal";
    }
}
