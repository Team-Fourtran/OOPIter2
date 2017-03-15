package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class EnergyHarvestStrategy extends HarvestStrategy {
	private WorkRadius harvestRadius;
	private ResourceStructure harvester;
	
	public EnergyHarvestStrategy(WorkRadius harvestRadius, ResourceStructure harvester) {
		super(harvestRadius, harvester);
		this.harvestRadius = harvestRadius;
		this.harvester = harvester;
	}

	@Override
	public int harvest(TileAssociation target) {
		ArrayList<TileAssociation> availableTiles = harvestRadius.getTilesWithResource("energy");
		if (availableTiles.contains(target)) {
			// deplete from this tile
			ResourcePackage resources = target.occupyResourcePackage(); // the strategy takes over resource package
			int count = resources.harvestEnergy();
			harvester.addResourceCount("energy", count);
			target.deoccupyResourcePackage();
			return count;
		} else {
			return 0;
		}
	}
}
