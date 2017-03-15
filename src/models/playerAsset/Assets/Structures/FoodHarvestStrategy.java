package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.Radius;
import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class FoodHarvestStrategy extends HarvestStrategy {
	private WorkRadius harvestRadius;
	private TileAssociation harvestTile;
	
	public FoodHarvestStrategy(WorkRadius harvestRadius) {
		super(harvestRadius);
	}

	@Override
	protected int specificHarvest(ResourcePackage resources) {
		return resources.harvestFood();
	}
	
}
