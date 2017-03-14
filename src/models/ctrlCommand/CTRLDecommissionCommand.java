package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;
import models.visitor.MapDecommissionVisitor;

public class CTRLDecommissionCommand implements CTRLCommand{
    private CombatAsset asset;

    private boolean isConfigured;

    public CTRLDecommissionCommand(){
        isConfigured = false;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.asset = (CombatAsset) parts.getRequestingAsset();
        isConfigured = true;
        parts.requestExecution();
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            asset.accept(
                    new MapDecommissionVisitor(map, player)
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Decommission";
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        //unused
    }
}
