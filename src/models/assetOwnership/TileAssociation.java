package models.assetOwnership;


import models.playerAsset.Assets.PlayerAsset;
import models.tileInfo.Tile;
import models.utility.Direction;
import models.visitor.TileVisitor;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TileAssociation extends Observable{
    private Tile tile;
    private AssetOwner assetOwner;
    private HashMap<Direction, TileAssociation> neighbors = new HashMap<Direction, TileAssociation>();
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


    public void remove(PlayerAsset ... p){
        for (PlayerAsset _p : p){
            assetOwner.removeAsset(_p);
        }
        notifyObservers();
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
    
    public void accept(TileVisitor v) {
    	tile.accept(v);
    	assetOwner.accept(v);
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
    
}
