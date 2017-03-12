package models.assetOwnership;

import java.util.Vector;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.PlayerAsset;

/*
 * This class keeps track of the relationship between a player asset and its influenced tiles
 */
public class RadiusOfInfluenceAssociation {
	private CombatAsset asset;
	private TileAssociation baseTile;
	private Vector<TileAssociation> influencedTiles;
	
	public RadiusOfInfluenceAssociation(CombatAsset asset, TileAssociation baseTile) {
		this.asset = asset;
		this.baseTile = baseTile;
		this.influencedTiles = assignTileAssociationsWithinRadius(asset);
	}
	
	public Vector<TileAssociation> getInfluencedTiles() {
		return influencedTiles;
	}
	
	public Vector<TileAssociation> updateInfluencedTiles() {
		influencedTiles = assignTileAssociationsWithinRadius(asset);
		return influencedTiles;
	}
	
	private Vector<TileAssociation> assignTileAssociationsWithinRadius(CombatAsset asset) {
		Vector<TileAssociation> vec = new Vector<TileAssociation>();
		TileAssociation start = baseTile;
		assignTileAssociationsWithinRadius(start, asset.getRadiusOfInfluence(), vec);
		return vec;
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
