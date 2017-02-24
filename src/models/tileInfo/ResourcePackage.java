package models.tileInfo;

/*
 * An encapsulation of resources (food, building materials, research materials), to be kept in a TileInfo object
 * Allows for harvesting of these resources one unit at a time.
 */
public class ResourcePackage {
	private int foodCount, buildingMaterialsCount, researchMaterialsCount;
	
	/*
	 *  Initialize the count of each resource type
	 */
	
	public ResourcePackage() {
		this.foodCount = 0;
		this.buildingMaterialsCount = 0;
		this.researchMaterialsCount = 0;
	}
	
	public ResourcePackage(int foodCount, int buildingMaterialsCount, int researchMaterialsCount) {
		this.foodCount = foodCount;
		this.buildingMaterialsCount = buildingMaterialsCount;
		this.researchMaterialsCount = researchMaterialsCount;
	}
	
	// Set the count of food, building materials, or research materials
	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
	
	public void setBuildingMaterialsCount(int buildingMaterialsCount) {
		this.buildingMaterialsCount = buildingMaterialsCount;
	}
	
	public void setResearchMaterialsCount(int researchMaterialsCount) {
		this.researchMaterialsCount = researchMaterialsCount;
	}
	
	/*
	 *  Get the count of food, building materials, or research materials
	 */
	public int getFoodCount() {
		return foodCount;
	}
	
	public int getBuildingMaterialsCount() {
		return buildingMaterialsCount;
	}
	
	public int getResearchMaterialsCount() {
		return researchMaterialsCount;
	}
	
	/*
	 *  Allow the harvest of all of the units of one resource type at a time
	 */
	public int harvestFood() {
		int harvest = foodCount;
		foodCount = 0;
		return harvest;
	}
	
	public int harvestBuildingMaterialsCount() {
		int harvest = buildingMaterialsCount;
		buildingMaterialsCount = 0;
		return harvest;
	}
	
	public int harvestResearchMaterialsCount() {
		int harvest = researchMaterialsCount;
		researchMaterialsCount = 0;
		return harvest;
	}
}
