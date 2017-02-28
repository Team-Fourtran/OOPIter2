package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.CombatAsset;
import models.playerAsset.Player;
import models.visitor.MapDecommissionVisitor;

public class CTRLDecommissionCommand implements CTRLCommand{
    private CombatAsset asset;

    public CTRLDecommissionCommand(CombatAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute(GameMap map, Player player) {
        asset.accept(
                new MapDecommissionVisitor(map, player)
        );
    }
}
