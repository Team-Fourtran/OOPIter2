package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.NonCombatAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

public class AssetAdditionVisitor implements AssetVisitor {
	GameMap map;
	TileAssociation location;
	
	public AssetAdditionVisitor(GameMap map, TileAssociation location) {
		this.map = map;
		this.location = location;
	}

	@Override
	public void visitUnit(Unit unit) {
		// add to tile association
		location.add(unit);
		// tell map to add to influence list
		map.addInfluenceRadius(unit, location);
	}

	@Override
	public void visitArmy(Army army) {
		location.add(army);
		map.addInfluenceRadius(army, location);
	}

	@Override
	public void visitStructure(Structure structure) {
		location.add(structure);
		map.addInfluenceRadius(structure, location);
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		// rally point is not a combat asset so it does not need an influence radius
		location.add(rallyPoint);
	}
}
