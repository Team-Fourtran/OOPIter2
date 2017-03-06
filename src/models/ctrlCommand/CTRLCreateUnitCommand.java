package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.command.CreateUnitCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLCreateUnitCommand implements CTRLCommand {
    private boolean isConfigured;
    private Structure structure;
    private String unitType;

    public CTRLCreateUnitCommand(){
        isConfigured = false;
    }

    public void configure(Structure structure, String unitType){
        this.structure = structure;
        this.unitType = unitType;
        isConfigured = true;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
        if(isConfigured){
            structure.addCommand(
                    new CreateUnitCommand(map, player, structure, unitType)
            );
        } else{
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }
}