package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class EnergyHarvestStrategy extends HarvestStrategy {
	private WorkRadius harvestRadius;
	private TileAssociation harvestTile;
	
	public EnergyHarvestStrategy(WorkRadius harvestRadius) {
		super(harvestRadius);
	}

	@Override
	protected int specificHarvest(ResourcePackage resources) {
		return resources.harvestEnergy();
	}
}
