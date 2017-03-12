package models.assetOwnership;

import models.playerAsset.Assets.PlayerAsset;
import models.visitor.AssetVisitor;
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

    protected boolean removeAsset(PlayerAsset p){
        if(assetList.remove(p)){
            numAssetsOwned--;
            return true;
        }
        return false;
    }

    protected void addAsset(PlayerAsset p){
        assetList.add(p);
        numAssetsOwned++;
    }

    protected int getNumAssetsOwned(){
        return numAssetsOwned;
    }
    
    protected void accept(AssetVisitor v) {
    	for (int i = 0; i < assetList.size(); i++) {
    		assetList.get(i).accept(v);
    	}
    }
}
