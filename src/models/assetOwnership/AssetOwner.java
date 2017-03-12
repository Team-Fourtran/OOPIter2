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

    public boolean hasAsset(PlayerAsset asset){
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
    
    public void accept(AssetVisitor v) {
    	for (int i = 0; i < assetList.size(); i++) {
    		assetList.get(i).accept(v);
    	}
    }
}
