package models.assetOwnership;

import java.util.Vector;

import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.PlayerAsset;
import models.visitor.AssetApproachVisitor;
import models.visitor.SpecificAssetVisitor;

/*
 * This class keeps track of the relationship between a player asset and its influenced tiles
 */
public class RadiusOfInfluenceAssociation extends Radius implements TileObserver {
	private CombatAsset asset;
	private GameMap map;
	
	public RadiusOfInfluenceAssociation(CombatAsset asset, TileAssociation baseTile, GameMap map) {
		super(baseTile, asset.getRadiusOfInfluence());
		this.asset = asset;
		this.map = map;
		// register itself as an observer of each of those tiles
		for (int i = 0; i < getTilesWithinRadius().size(); i++) {
			getTilesWithinRadius().get(i).addObserver(this);
		}
	}

	@Override
	public Vector<TileAssociation> updateInfluencedTiles() {
		// need to no longer be observer of the old ones
		setRadiusSize(asset.getRadiusOfInfluence());
		for (int i = 0; i < getTilesWithinRadius().size(); i++) {
			getTilesWithinRadius().get(i).removeObserver(this);
		}
		
		assignTileAssociationsWithinRadius();
		for (int i = 0; i < getTilesWithinRadius().size(); i++) {
			getTilesWithinRadius().get(i).addObserver(this);
		}
		return getTilesWithinRadius();
	}

	/*
	 * Something entered the radius of influence in TileAssociation tA
	 */
	@Override
	public void updateAdd(TileAssociation tA, PlayerAsset approachingAsset) {
		if (PlayerAssetOwnership.getPlayerOwnership(approachingAsset) != PlayerAssetOwnership.getPlayerOwnership(asset)) {
			SpecificAssetVisitor v = new AssetApproachVisitor(map, tA, approachingAsset);
			asset.accept(v);
		}
	}

	@Override
	public void updateRemove(TileAssociation tileAssociation, PlayerAsset p) {
		// TODO implement asset leaving visitor
		
	}
}
