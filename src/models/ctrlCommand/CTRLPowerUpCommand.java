package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.command.PowerUpCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLPowerUpCommand implements CTRLCommand{
    private CombatAsset asset;

    private boolean isConfigured;

    public CTRLPowerUpCommand(){
        isConfigured = false;
    }

    public void configure(CombatAsset asset){
        this.asset = asset;
    }

   @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        isConfigured = true;
        this.asset = (CombatAsset) parts.getRequestingAsset();
        parts.requestExecution();
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            asset.clearQueue();
            asset.addCommand(
                    new PowerUpCommand(asset)
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Power Up";
    }
}
