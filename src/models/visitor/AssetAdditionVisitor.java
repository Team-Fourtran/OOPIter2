package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.NonCombatAsset;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
/*
 * This visitor assists with adding assets to tile associations
 */
public class AssetAdditionVisitor implements AssetVisitor {
	GameMap map;
	TileAssociation location;
	
	public AssetAdditionVisitor(GameMap map, TileAssociation location) {
		this.map = map;
		this.location = location;
	}

	@Override
	public void visitUnit(Unit unit) {
		addWithInfluence(unit);
	}

	@Override
	public void visitArmy(Army army) {
		addWithInfluence(army);
	}

	@Override
	public void visitStructure(Structure structure) {
		addWithInfluence(structure);
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		// rally point is not a combat asset so it does not need an influence radius
		location.add(rallyPoint);
	}
	
	private void addWithInfluence(CombatAsset asset) {
		location.add(asset);
		map.addInfluenceRadius(asset, location);
		map.addVisibilityRadius(asset, location);
	}
}
