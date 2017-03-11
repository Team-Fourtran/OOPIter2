package models.tileInfo;

import models.visitor.TileVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Tile {

    private Terrain terrainType;
    private ResourcePackage myResources;
    private ArrayList<AoE> aoE;
    private ArrayList<OneShotItem> oneShotItems;
    private ObstacleItem obstacleItem;

    public Tile(Terrain terrain, ArrayList<OneShotItem> itemList){
        this.terrainType = terrain;
        this.aoE = new ArrayList<>();
        this.oneShotItems = new ArrayList<>();
        this.oneShotItems = itemList;
    }
    public Tile(Terrain t, ResourcePackage p, ArrayList<AoE> a, ArrayList<OneShotItem> i) {
        this.terrainType = t;
        this.myResources = p;
        this.aoE = a;
        this.oneShotItems = i;
    }

    public boolean hasItems(){
        return !oneShotItems.isEmpty();
    }

    public ArrayList<OneShotItem> interactWithItems(){
        ArrayList<OneShotItem> _items = new ArrayList<>(oneShotItems);
        oneShotItems.clear();
        return _items;
    }

    public double getMovementCost() {
        return terrainType.getMovementCost();
    }

    public Terrain getTerrainType() {
        return terrainType;
    }

    public ArrayList<AoE> getAoEs() {
        return aoE;
    }

    public void accept(TileVisitor v) {
    	terrainType.accept(v);
    	for (Item i : oneShotItems){
    	    i.accept(v);
        }
        for (AoE aoe : aoE){
    	    aoe.accept(v);
        }
    }
}
