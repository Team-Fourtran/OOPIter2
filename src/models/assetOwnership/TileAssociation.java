package models.assetOwnership;


import models.playerAsset.Assets.PlayerAsset;
import models.tileInfo.Item;
import models.tileInfo.OneShotItem;
import models.tileInfo.Tile;
import models.utility.Direction;
import models.visitor.*;

import java.util.Observable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TileAssociation extends Observable{
    private Tile tile;
    private AssetOwner assetOwner;
    private HashMap<Direction, TileAssociation> neighbors = new HashMap<Direction, TileAssociation>();
    private Vector<Observer> observers = new Vector<>(0);

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
        boolean removal = assetOwner.removeAsset(p);
        notifyObservers();
        return removal;
    }

    public void removeItem(){
        tile.removeItem();
    }

    public void add(PlayerAsset p){
        assetOwner.addAsset(p);
        notifyObservers();
    }

    // Eventually have a method for removing Resources
    
    public Collection<TileAssociation> getNeighbors(){
    	neighbors.values();
        return neighbors.values();
    }

    public void setNeighbor(Direction d, TileAssociation t){
        neighbors.put(d, t);
    }

    public double getMovementCost() {
        return tile.getMovementCost();
    }

    public int debugNumAssets(){
        return assetOwner.getNumAssetsOwned();
    }
    
    public void accept(ObjectVisitor v) {
        if (v instanceof TileVisitor){
            tile.accept((TileVisitor) v);
        }
        if (v instanceof AssetVisitor) {
            assetOwner.accept((AssetVisitor) v);
        }
    }
    
    @Override
    public void notifyObservers(){
        for(Observer ob : observers){
            ob.update(this);
        }
    }
    
    public void addObserver(Observer o) {
    	observers.add(o);
    }
    
    public void removeObserver(Observer o) {
    	observers.remove(o);
    }
    
}
