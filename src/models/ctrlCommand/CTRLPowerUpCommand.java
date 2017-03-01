package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.command.PowerUpCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLPowerUpCommand implements CTRLCommand{
    private CombatAsset asset;

    public CTRLPowerUpCommand(CombatAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute(GameMap map, Player player) {
        asset.clearQueue();
        asset.addCommand(
                new PowerUpCommand(asset)
        );
    }
}
