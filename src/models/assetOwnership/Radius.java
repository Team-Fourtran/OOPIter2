package models.assetOwnership;

import java.util.Vector;

import models.playerAsset.Assets.CombatAsset;

public class Radius {
	private int radiusSize;
	private TileAssociation baseTile;
	private Vector<TileAssociation> influencedTiles;
	
	public Radius(TileAssociation baseTile, int radiusSize) {
		this.baseTile = baseTile;
		this.radiusSize = radiusSize;
		assignTileAssociationsWithinRadius();
	}
	
	public Vector<TileAssociation> getInfluencedTiles() {
		return influencedTiles;
	}
	
	public void setRadiusSize(int radiusSize) {
		this.radiusSize = radiusSize;
	}
	
	// for when the influence tiles need to change. Such as when radius is increased
	public Vector<TileAssociation> updateInfluencedTiles() {
		influencedTiles = assignTileAssociationsWithinRadius();
		return influencedTiles;
	}

	protected Vector<TileAssociation> assignTileAssociationsWithinRadius() {
		Vector<TileAssociation> vec = new Vector<TileAssociation>();
		TileAssociation start = baseTile;
		assignTileAssociationsWithinRadius(start, radiusSize, vec);
		influencedTiles = vec;
		return influencedTiles;
	}
	
	private void assignTileAssociationsWithinRadius(TileAssociation s, int radius, Vector<TileAssociation> vec) {
		if (!vec.contains(s)) {
			vec.add(s);
		}
		if (radius != 0) {
			for (TileAssociation t : s.getNeighbors()) {
				assignTileAssociationsWithinRadius(t, radius-1, vec);
			}
		}
	}
}
