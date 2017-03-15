package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public abstract class HarvestStrategy {
	private WorkRadius harvestRadius;
	private ResourceStructure harvester;
	
	public HarvestStrategy(WorkRadius harvestRadius, ResourceStructure harvester) {
		this.harvestRadius = harvestRadius;
		this.harvester = harvester;
	}
	
	public abstract int harvest(TileAssociation target);
}
