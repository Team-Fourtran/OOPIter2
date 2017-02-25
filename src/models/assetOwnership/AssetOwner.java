package models.assetOwnership;

import models.playerAssetNew.PlayerAsset;
import models.visitor.AssetVisitor;
import models.visitor.TileVisitor;

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

    public boolean removeAsset(PlayerAsset p){
        if(assetList.remove(p)){
            numAssetsOwned--;
            return true;
        }
        return false;
    }

    public void addAsset(PlayerAsset p){
        assetList.add(p);
        numAssetsOwned++;
    }

    public int getNumAssetsOwned(){
        return numAssetsOwned;
    }
    
    public void accept(AssetVisitor v) {
    	for (int i = 0; i < assetList.size(); i++) {
    		assetList.get(i).accept(v);
    	}
    }
}
