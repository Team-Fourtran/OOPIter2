package models.playerAsset.Assets.Structures;

import java.util.ArrayList;

import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.tileInfo.ResourcePackage;

public class OreHarvestStrategy extends HarvestStrategy {
	private WorkRadius harvestRadius;
	private TileAssociation harvestTile;
	
	public OreHarvestStrategy(WorkRadius harvestRadius) {
		super(harvestRadius);
	}

	@Override
	protected int specificHarvest(ResourcePackage resources) {
		System.out.println("Hello?");
		return resources.harvestOre();
	}
}
