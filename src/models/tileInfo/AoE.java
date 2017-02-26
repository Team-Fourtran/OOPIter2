package models.tileInfo;

/*
 * This interface is for AoE which can provide the results of their effect (in how many health points to add/remove)
 * AoEs are added to tiles, which apply effects to the assets on them
 */
public interface AoE {
	// Returns double representing a double to add to a player assets' existing health
	public double getAreaEffect();
}
