package models.assetOwnership;


import models.playerAssetNew.PlayerAsset;
import models.tileInfo.Tile;
import models.visitor.TileVisitor;

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

    public boolean remove(PlayerAsset p){
        return assetOwner.removeAsset(p);
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
    
    public void accept(TileVisitor v) {
    	tile.accept(v);
    	assetOwner.accept(v);
    }

}
