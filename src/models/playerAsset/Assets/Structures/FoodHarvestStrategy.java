package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.Radius;
import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class FoodHarvestStrategy extends HarvestStrategy {
	private WorkRadius harvestRadius;
	private ResourceStructure harvester;
	
	public FoodHarvestStrategy(WorkRadius harvestRadius, ResourceStructure harvester) {
		super(harvestRadius, harvester);
		this.harvestRadius = harvestRadius;
		this.harvester = harvester;
	}

	@Override
	public int harvest(TileAssociation target) {
		ArrayList<TileAssociation> availableTiles = harvestRadius.getTilesWithResource("food");
		if (availableTiles.contains(target)) {
			// deplete from this tile
			ResourcePackage resources = target.occupyResourcePackage(); // the strategy takes over resource package
			int count = resources.harvestFood();
			harvester.addResourceCount("food", count);
			target.deoccupyResourcePackage();
			return count;
		} else {
			return 0;
		}
	}
}
