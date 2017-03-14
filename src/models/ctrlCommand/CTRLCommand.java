package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;

public interface CTRLCommand{
    void callback() throws CommandNotConfiguredException; //Single endpoint called when new async data is ready.
    void configure(CommandComponents parts) throws CommandNotConfiguredException;
    void execute(GameMap map, Player player) throws CommandNotConfiguredException;
    boolean isConfigured();
    String toString();
}

