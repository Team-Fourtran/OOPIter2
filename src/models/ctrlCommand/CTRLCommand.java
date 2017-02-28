package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;

public interface CTRLCommand {
    void execute(GameMap map, Player player);
}
