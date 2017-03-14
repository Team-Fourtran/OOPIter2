package models.playerAsset.Assets.Structures;

import models.assetOwnership.TileAssociation;
import models.tileInfo.ResourcePackage;

public interface HarvestStrategy {
	public TileAssociation harvest();
}
