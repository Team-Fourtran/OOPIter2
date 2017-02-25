package models.assetOwnership;


import models.playerAssetNew.PlayerAsset;
import models.tileInfo.Tile;
import models.utility.Observer;
import models.visitor.TileVisitor;

import java.util.ArrayList;

public class TileAssociation {
    private Tile tile;
    private AssetOwner assetOwner;
    private ArrayList<TileAssociation> neighbors = new ArrayList<>(0);
    private ArrayList<Observer> observers = new ArrayList<>(0);

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
    	notifyObservers();
        return assetOwner.removeAsset(p);
    }

    public void add(PlayerAsset p){
    	notifyObservers();
        assetOwner.addAsset(p);
    }

    // Eventually have a method for removing Resources
    
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
    
    public void attach(Observer o) {
    	observers.add(o);
    }
    
    public void notifyObservers() {
    	for (int i = 0; i < observers.size(); i++) {
    		observers.get(i).update();
    	}
    }

}
