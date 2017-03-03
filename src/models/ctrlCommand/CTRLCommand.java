package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;

public interface CTRLCommand{
    CTRLCommand clone();
    void execute(GameMap map, Player player) throws Exception;
    /*
     * Also needs to implement a configure() operation that gives concrete commands values for their attributes.
      * Concrete Commands' constructors should only set an isConfigured flag to false.
      * */
}

class CommandNotConfiguredException extends Exception {

    public CommandNotConfiguredException(String message) {
        super(message);
    }

    public CommandNotConfiguredException(String message, Throwable throwable) {
        super(message, throwable);
    }

}