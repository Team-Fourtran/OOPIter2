package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;

public interface CTRLCommand{
    void execute(GameMap map, Player player) throws CommandNotConfiguredException;
    /*
     * Also needs to implement a configure() operation that gives concrete commands values for their attributes.
      * Concrete Commands' constructors should only set an isConfigured flag to false.
      * */
}

