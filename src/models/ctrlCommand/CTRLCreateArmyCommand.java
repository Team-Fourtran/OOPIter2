package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.ArmyCreationVisitor;

public class CTRLCreateArmyCommand implements CTRLCommand{
    private TileAssociation RPLocation;
    private Unit[] units;

    private boolean isConfigured;

    public CTRLCreateArmyCommand(){
        isConfigured = false;
    }

    public void configure(TileAssociation startTile, Unit ... units){
        isConfigured = true;
        this.RPLocation = startTile;
        this.units = units;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        isConfigured = true;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            player.accept(
                    new ArmyCreationVisitor(map, player, RPLocation, units)
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Create Army";
    }
}
