package models.tileInfo;
/*
 * This interface is for retrieving the effects of an item in terms of how many health points to gain/remove
 */
public interface Item {
	// Returns a double representing how many health points to add to a PlayerAsset's health
	public double getItemEffect();
}
