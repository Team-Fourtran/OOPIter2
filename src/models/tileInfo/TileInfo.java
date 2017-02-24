package models.tileInfo;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * TileInfo contains the terrain, resources, AoEs, items (decal items as well). 
 * The only required aspect is Terrain.
 * TileInfo allows one to gain access to these components and interact with them.
 */
public class TileInfo {
	private Terrain terrainType;
	private ResourcePackage myResources;
	private ArrayList<AoE> aoE;
	private ArrayList<Item> items;
	
	public TileInfo(Terrain terrain) {
		this.terrainType = terrain;
		this.aoE = new ArrayList<AoE>();
		this.items = new ArrayList<Item>();
	}
	
	public TileInfo(Terrain t, ResourcePackage p, ArrayList<AoE> a, ArrayList<Item> i) {
		this.terrainType = t;
		this.myResources = p;
		this.aoE = a;
		this.items = i;
	}
	
	// Get methods, to obtain the objects this TileInfo contains
	public Terrain getTerrainType() {
		return terrainType;
	}
	
	/*
	 *  This get method does not remove the object's ResourcePackage. 
	 *  Mainly intended for the View to parse through a tile's resource contents to represent it.
	 */
	public ResourcePackage getResourcePacakge() {
		return myResources;
	}
	
	/*
	 * Retrieve list of AoEs in tile
	 */
	public ArrayList<AoE> getAoEs() {
		return aoE;
	}
	
	/*
	 * Retrieve list of Items in tile
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void setResourcePackage(ResourcePackage resources) {
		this.myResources = resources;
	}
	
	// Add AoEs. Provides ability to add multiple AoEs with one call
	public void addAoEs(AoE... aoEs) {
		for (int i = 0; i < aoEs.length; i++) {
			this.aoE.add(aoEs[i]);
		}
	}
	
	// Add Items. Provides ability to add multiple Items with one call
	public void addItems(Item... items) {
		for (int i = 0; i < items.length; i++) {
			this.items.add(items[i]);
		}
	}
	
	// Returns double representing the cost for moving onto this tile, based upon Terrain type
	public double getMovementCost() {
		return terrainType.getMovementCost();
	}
	
	// Returns boolean signifying whether or not the tile is blocked
	public boolean isBlocked() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof ObstacleItem) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 *  Similar to getResourcePacakge, but it removes the TileInfo's reference to the object,
	 *  as it has been "harvested"
	 */
	public ResourcePackage harvestResources() {
		ResourcePackage harvest = myResources;
		myResources = null;
		return harvest;
	}
	
	/*
	 * This method returns a double representing the total effect that each of its components.
	 * This double can be used to add to a PlayerAsset's health status.
	 */
	public double getTileEffect() {
		double totalEffect = 0;
		for (int i = 0; i < aoE.size(); i++) {
			totalEffect += aoE.get(i).getAreaEffect();
		}
		
		Iterator<Item> i = items.iterator();
		while (i.hasNext()) {
			Item currentItem = i.next();
			totalEffect += currentItem.getItemEffect();
			
			// If the item is a one shot item, remove it from the list.
			if (currentItem.getClass().getSimpleName().equals("OneShotItem")) {
				i.remove();
			}
		}
		return totalEffect;
	}
}
