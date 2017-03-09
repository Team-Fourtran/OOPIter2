package models.playerAsset.Assets;

import java.util.ArrayList;

/**
 * Created by Clay on 3/8/2017.
 */
public interface ResourceStructure {

    public ArrayList<Worker> removeWorkersFromGathering(int num);
    public ArrayList<Worker> removeWorkersFromProduction(int num);
    public void addWorkersToGathering(ArrayList<Worker> list);
    public void addWorkersToProduction(ArrayList<Worker> list);
}
