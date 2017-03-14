package models.assetOwnership;

import java.util.Vector;

import models.playerAsset.Assets.CombatAsset;

public class Radius {
	private CombatAsset asset;
	private TileAssociation baseTile;
	private Vector<TileAssociation> influencedTiles;
}
