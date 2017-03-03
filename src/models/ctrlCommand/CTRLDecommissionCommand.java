package models.ctrlCommand;

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

    public void configure(CombatAsset asset){
        isConfigured = true;
        this.asset = asset;
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
}
