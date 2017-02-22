package models.tileInfo;

/*
 * See Terrain interface. This provides a movement cost, affecting the preference of an asset to make a path
 * through that tile with this terrain.
 */
public class Slowing implements Terrain {
	private double movementCost = 2;
	
	@Override
	// Returns movement cost for units/armies. Cost is in the interval [0,inf].
	public double getMovementCost() {
		return movementCost;
	}

}
