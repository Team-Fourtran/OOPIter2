package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.command.PowerDownCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLPowerDownCommand implements CTRLCommand{
    private CombatAsset asset;

    private boolean isConfigured;

    public CTRLPowerDownCommand(){
        isConfigured = false;
    }
    public void configure(CombatAsset asset){
        isConfigured = true;
        this.asset = asset;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        isConfigured = true;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            asset.clearQueue();
            asset.addCommand(
                    new PowerDownCommand(asset)
            );
        } else {
           throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Power Down";
    }
}
