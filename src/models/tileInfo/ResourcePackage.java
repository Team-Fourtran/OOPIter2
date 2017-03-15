package models.tileInfo;

/*
 * An encapsulation of resources (food, building materials, research materials), to be kept in a TileInfo object
 * Allows for harvesting of these resources one unit at a time.
 */
public class ResourcePackage {
	private int foodCount, oreCount, energyCount;
	
	/*
	 *  Initialize the count of each resource type
	 */
	
	public ResourcePackage() {
		this.foodCount = 0;
		this.oreCount = 0;
		this.energyCount = 0;
	}
	
	public ResourcePackage(int foodCount, int oreCount, int energyCount) {
		this.foodCount = foodCount;
		this.oreCount = oreCount;
		this.energyCount = energyCount;
	}
	
	// Set the count of food, building materials, or research materials
	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
	
	public void setOreCount(int oreCount) {
		this.oreCount = oreCount;
	}
	
	public void setEnergyCount(int energyCount) {
		this.energyCount = energyCount;
	}
	
	/*
	 *  Get the count of food, building materials, or research materials
	 */
	public int getFoodCount() {
		return foodCount;
	}
	
	public int getOreCount() {
		return oreCount;
	}
	
	public int getEnergyCount() {
		return energyCount;
	}
	
	/*
	 *  Allow the harvest of all of the units of one resource type at a time
	 */
	public int harvestFood() {
		if (foodCount >= 10) {
			foodCount -= 10; 
			return 10;
		} else {
			int remainingFood = foodCount;
			foodCount = 0;
			return remainingFood;
		}
	}
	
	public int harvestOre() {
		if (oreCount >= 10) {
			oreCount -= 10; 
			return 10;
		} else {
			int remainingFood = oreCount;
			oreCount = 0;
			return remainingFood;
		}
	}
	
	public int harvestEnergy() {
		if (energyCount >= 10) {
			energyCount -= 10; 
			return 10;
		} else {
			int remainingFood = energyCount;
			energyCount = 0;
			return remainingFood;
		}
	}
}
