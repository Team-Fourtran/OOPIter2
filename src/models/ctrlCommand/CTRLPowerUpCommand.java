package models.ctrlCommand;

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

    public void configure (CombatAsset asset){
        isConfigured = true;
        this.asset = asset;
    }

    @Override
    public CTRLCommand clone() {
        return new CTRLPowerUpCommand();
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
}
