package models.playerAsset.Iterators;

import models.ctrlCommand.CTRLCommand;

public interface AssetIterator extends Iterator2 {
    void update();
    void nextInstance();
    void prevInstance();
    String getCurrentMode();

    void nextCommand();
    void prevCommand();
    CTRLCommand getCommand();
}
