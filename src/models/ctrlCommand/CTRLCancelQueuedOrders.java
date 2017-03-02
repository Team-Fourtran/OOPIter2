package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;

public class CTRLCancelQueuedOrders implements CTRLCommand{
    private PlayerAsset asset;

    public CTRLCancelQueuedOrders(PlayerAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute(GameMap map, Player player){
        asset.clearQueue();
    }
}
