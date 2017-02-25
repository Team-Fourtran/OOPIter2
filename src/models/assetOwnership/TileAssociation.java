package models.assetOwnership;


import models.playerAsset.PlayerAsset;
import models.tileInfo.Tile;

import java.util.ArrayList;

public class TileAssociation {
    private Tile tile;
    private AssetOwner assetOwner;
    private ArrayList<TileAssociation> neighbors = new ArrayList<>(0);

    public TileAssociation(Tile t){
        this.tile = t;
        this.assetOwner = new AssetOwner();
    }

    public boolean isAssetOwner(){
        return (assetOwner.getNumAssetsOwned() > 0);
    }

    public boolean isAssetOwner(PlayerAsset asset){
        return (assetOwner.hasAsset(asset));
    }

    public void remove(PlayerAsset ... p){
        for (PlayerAsset _p : p){
            assetOwner.removeAsset(_p);
        }
    }

    public void add(PlayerAsset p){
        assetOwner.addAsset(p);
    }

    public ArrayList<TileAssociation> getNeighbors(){
        return neighbors;
    }

    public void setNeighbor(TileAssociation t){
        neighbors.add(t);
    }

    public double getMovementCost() {
        return tile.getMovementCost();
    }

    public int debugNumAssets(){
        return assetOwner.getNumAssetsOwned();
    }

}
