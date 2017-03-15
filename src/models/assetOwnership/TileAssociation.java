package models.assetOwnership;


import models.playerAsset.Assets.PlayerAsset;
import models.tileInfo.Item;
import models.tileInfo.OneShotItem;
import models.tileInfo.ResourcePackage;
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
    private Vector<TileObserver> observers = new Vector<>(0);

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
    
    public AssetOwner getAssetOwner() {
    	return assetOwner;
    }

    public boolean remove(PlayerAsset p){
        boolean removal = assetOwner.removeAsset(p);
        notifyObserversRemove(p);
        return removal;
    }

    public void removeItem(){
        tile.removeItem();
    }

    public void add(PlayerAsset p){
        assetOwner.addAsset(p);
        notifyObserversAdd(p);
    }

    public ResourcePackage getResourcePackage() {
    	return tile.getResourcePackage();
    }

    public boolean hasResourcePackage(){
        return tile.hasResourcePackage();
    }
    
    public ResourcePackage occupyResourcePackage() {
    	return tile.occupyResourcePackage();
    }
    
    public void deoccupyResourcePackage() {
    	tile.deoccupyResourcePackage();
    }
    
    // Eventually have a method for removing Resources
    
    public Collection<TileAssociation> getNeighbors(){
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
    
    public void notifyObserversAdd(PlayerAsset p) {
        for(TileObserver ob : observers){
            ob.updateAdd(this, p);
        }
    }
    
    public void notifyObserversRemove(PlayerAsset p) {
        for(TileObserver ob : observers){
            ob.updateRemove(this, p);
        }
    }
    
    public void addObserver(TileObserver o) {
    	observers.add(o);
    }
    
    public void removeObserver(TileObserver o) {
    	observers.remove(o);
    }
    
}
