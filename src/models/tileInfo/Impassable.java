package models.tileInfo;

import models.visitor.TileVisitor;

/*
 * See Terrain interface. This provides an 'infinity' cost, making it impossible for an asset to make a path
 * through that tile with this terrain.
 */
public class Impassable implements Terrain {
	private double movementCost = Double.POSITIVE_INFINITY;
	
	@Override
	// Returns movement cost for units/armies. Cost is in the interval [0,inf].
	public double getMovementCost() {
		return movementCost;
	}

	@Override
	public void accept(TileVisitor v) {
		v.visitImpassable(this);
	}

}
