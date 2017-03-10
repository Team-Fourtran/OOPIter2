package models.tileInfo;

import models.visitor.TileVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Tile {

    private Terrain terrainType;
    private ResourcePackage myResources;
    private ArrayList<AoE> aoE;
    private ArrayList<Item> items;

    public Tile(Terrain terrain, ArrayList<Item> itemList){
        this.terrainType = terrain;
        this.aoE = new ArrayList<>();
        this.items = new ArrayList<>();
        this.items = itemList;
    }
    public Tile(Terrain t, ResourcePackage p, ArrayList<AoE> a, ArrayList<Item> i) {
        this.terrainType = t;
        this.myResources = p;
        this.aoE = a;
        this.items = i;
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

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public void accept(TileVisitor v) {
    	terrainType.accept(v);
    	for (Item i : items){
    	    i.accept(v);
        }
        for (AoE aoe : aoE){
    	    aoe.accept(v);
        }
    }
}
