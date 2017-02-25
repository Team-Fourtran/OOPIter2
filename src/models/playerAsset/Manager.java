package src.models.playerAsset;

import java.util.ArrayList;

public interface Manager{

    public void addCommand(Command c);
    public void decommision();
    public int calculateTotalUpkeep();
    public void freeID(String assetID);
}