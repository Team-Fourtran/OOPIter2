package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;

public class CTRLCancelQueuedOrders implements CTRLCommand{
    private PlayerAsset asset;
    private boolean isConfigured;

    public CTRLCancelQueuedOrders(){
        isConfigured = false;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {

    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.asset = parts.getRequestingAsset();
        isConfigured = true;
        this.asset = parts.getRequestingAsset();
        parts.requestExecution();
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured)
            asset.clearQueue();
        else throw new CommandNotConfiguredException("[" + this + "] is not configured.");
    }

    @Override
    public String toString(){
        return "Cancel Queued Orders";
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        //unused
    }
}
