package models.playerAsset.Assets.Structures;

import models.assetOwnership.Radius;
import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class FoodHarvestStrategy implements HarvestStrategy {
	private WorkRadius harvestRadius;
	private TileAssociation harvestTile;
	
	public FoodHarvestStrategy(WorkRadius harvestRadius) {
		this.harvestRadius = harvestRadius;
	}

	@Override
	public TileAssociation harvest() {
		harvestTile = harvestRadius.getTileWithResource("food");
		return harvestTile;
	}
	
	
}
