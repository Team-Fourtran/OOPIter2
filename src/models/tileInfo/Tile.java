package models.tileInfo;

import models.visitor.TileVisitor;

public class Tile {

    private Terrain terrainType;
    private ResourcePackage myResources;
    private AoE aoE;
    private Item item;
    private boolean resourcesOccupied;

    public Tile(Terrain terrain, Item item){
        this.terrainType = terrain;
        this.item = item;
        this.myResources = new ResourcePackage();
    }
    public Tile(Terrain t, ResourcePackage p, AoE a, Item i) {
        this.terrainType = t;
        this.myResources = p;
        this.aoE = a;
        this.item = i;
        this.resourcesOccupied = false;
    }

    public void removeItem(){
        this.item = null;
    }

    public double getMovementCost() {
        return terrainType.getMovementCost();
    }

    public Terrain getTerrainType() {
        return terrainType;
    }

    public AoE getAoE() {
        return aoE;
    }
    
	public ResourcePackage getResourcePackage() {
		return myResources;
	}
    
    // can't retrieve resources if they are already being worked on
    public ResourcePackage occupyResourcePackage() {
    	if (!resourcesOccupied) {
    		resourcesOccupied = true;
    		return myResources;
    	} else {
    		return null;
    	}
    }
    
    public void deoccupyResourcePackage() {
    	resourcesOccupied = false;
    }

    public void accept(TileVisitor v) {
    	terrainType.accept(v);
    	if(item != null){
            item.accept(v);
        }
        if(aoE != null){
            aoE.accept(v);
        }
    }
}
