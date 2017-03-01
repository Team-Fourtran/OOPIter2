package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.command.PowerDownCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLPowerDownCommand implements CTRLCommand{
    private CombatAsset asset;

    public CTRLPowerDownCommand(CombatAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute(GameMap map, Player player) {
        asset.clearQueue();
        asset.addCommand(
                new PowerDownCommand(asset)
        );
    }
}
