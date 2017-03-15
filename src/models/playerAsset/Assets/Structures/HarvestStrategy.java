package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public abstract class HarvestStrategy {
	private WorkRadius harvestRadius;
	
	public HarvestStrategy(WorkRadius harvestRadius) {
		this.harvestRadius = harvestRadius;
	}
	
	public int harvest(TileAssociation target, ResourceStructure harvester) {
		ArrayList<TileAssociation> availableTiles = harvestRadius.getTilesWithResource("food");
		if (availableTiles.contains(target)) {
			// deplete from this tile
			ResourcePackage resources = target.occupyResourcePackage(); // the strategy takes over resource package
			int foodCount = specificHarvest(resources);
			harvester.addResourceCount("food", foodCount);
			target.deoccupyResourcePackage();
			return foodCount;
		} else {
			return 0;
		}
		// returns whether or not the harvest was successful or not
	}
	
	protected abstract int specificHarvest(ResourcePackage resources);
	
}
