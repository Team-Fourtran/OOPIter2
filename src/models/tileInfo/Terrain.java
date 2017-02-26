package models.tileInfo;

import models.visitor.TileVisitor;

/*
 * Defines interface for Terrain. Terrain affects the path chosen for assets to travel through
 */
public interface Terrain {
	// Returns movement cost for units/armies.
	public double getMovementCost();
	public void accept(TileVisitor v);
}
