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

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.structure = (Structure) parts.getRequestingAsset();
        isConfigured = true;
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
