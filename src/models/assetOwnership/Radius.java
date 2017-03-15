package models.assetOwnership;

import java.util.Vector;

import models.playerAsset.Assets.CombatAsset;

public abstract class Radius {
	private int radiusSize;
	private TileAssociation baseTile;
	private Vector<TileAssociation> influencedTiles;
	
	public Radius(TileAssociation baseTile, int radiusSize) {
		this.baseTile = baseTile;
		this.radiusSize = radiusSize;
		this.influencedTiles = assignTileAssociationsWithinRadius();
	}
	
	public Vector<TileAssociation> getTilesWithinRadius() {
		return influencedTiles;
	}

	public void setTilesWithinRadius(Vector<TileAssociation> influencedTiles) {
		this.influencedTiles =  influencedTiles;
	}

	public TileAssociation getBaseTile() {
		return baseTile;
	}

	public int getRadiusSize() {
		return radiusSize;
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
	
	protected void assignTileAssociationsWithinRadius(TileAssociation s, int radius, Vector<TileAssociation> vec) {
		if (!vec.contains(s)) {
			vec.add(s);
		}
		if (radius != 0) {
			for (TileAssociation t : s.getNeighbors()) {
				assignTileAssociationsWithinRadius(t, radius - 1, vec);
			}
		}
	}

}
