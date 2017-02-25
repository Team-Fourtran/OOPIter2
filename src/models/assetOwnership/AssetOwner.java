package models.assetOwnership;

import models.playerAsset.PlayerAsset;
import java.util.ArrayList;

/*
Should use visitor???????
 */


public class AssetOwner {
    private int numAssetsOwned;
    private ArrayList<PlayerAsset> assetList;

    AssetOwner(){
        assetList = new ArrayList<PlayerAsset>(0);
        this.numAssetsOwned = 0;
    }

    protected boolean hasAsset(PlayerAsset asset){
        return assetList.contains(asset);
    }

    public void removeAsset(PlayerAsset p){
        if(assetList.remove(p)){
            numAssetsOwned--;
        }
    }

    public void addAsset(PlayerAsset p){
        assetList.add(p);
        numAssetsOwned++;
    }

    public int getNumAssetsOwned(){
        return numAssetsOwned;
    }
}
