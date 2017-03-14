package models.playerAsset.Assets.Structures;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;

public class OreHarvestStrategy implements HarvestStrategy {
	private WorkRadius harvestRadius;
	private TileAssociation harvestTile;
	
	public OreHarvestStrategy(WorkRadius harvestRadius) {
		this.harvestRadius = harvestRadius;
	}

	@Override
	public TileAssociation harvest() {
		harvestTile = harvestRadius.getTileWithResource("ore");
		return harvestTile;
	}
	
	
}
