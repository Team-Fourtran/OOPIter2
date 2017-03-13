package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;

public interface CTRLCommand{
    void configure(CommandComponents parts) throws CommandNotConfiguredException;
    void execute(GameMap map, Player player) throws CommandNotConfiguredException;
    String toString();
}

