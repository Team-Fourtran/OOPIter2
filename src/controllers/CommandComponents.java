package controllers;

import models.ctrlCommand.CTRLCommand;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Iterators.CommandIterator;

public interface CommandComponents {
    PlayerAsset getInstance();
    CTRLCommand getCmd();
}
