package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;

public class CTRLCancelQueuedOrders implements CTRLCommand{
    private PlayerAsset asset;
    private boolean isConfigured;

    public CTRLCancelQueuedOrders(){
        isConfigured = false;
    }

    public void configure(PlayerAsset asset){
        isConfigured = true;
        this.asset = asset;
    }

    @Override
    public CTRLCommand clone() {
        return new CTRLCancelQueuedOrders();
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured)
            asset.clearQueue();
        else throw new CommandNotConfiguredException("[" + this + "] is not configured.");
    }
}