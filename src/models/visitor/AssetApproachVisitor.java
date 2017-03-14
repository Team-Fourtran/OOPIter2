package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.TileAssociation;
import models.command.AttackCommand;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Capital;
import models.playerAsset.Assets.Structures.Farm;
import models.playerAsset.Assets.Structures.Fort;
import models.playerAsset.Assets.Structures.Mine;
import models.playerAsset.Assets.Structures.ObservationTower;
import models.playerAsset.Assets.Structures.PowerPlant;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Structures.University;
import models.playerAsset.Assets.Units.Colonist;
import models.playerAsset.Assets.Units.Explorer;
import models.playerAsset.Assets.Units.MeleeUnit;
import models.playerAsset.Assets.Units.RangedUnit;
import models.playerAsset.Assets.Units.Unit;

public class AssetApproachVisitor implements SpecificAssetVisitor {
	GameMap map;
	TileAssociation t; // the tile to attack
	PlayerAsset approachingAsset;
	
	public AssetApproachVisitor(GameMap map,TileAssociation t, PlayerAsset approachingAsset) {
		this.map = map;
		this.t = t;
		this.approachingAsset = approachingAsset;
	}

	@Override
	public void visitUnit(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitArmy(Army army) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitStructure(Structure structure) {
		// TODO: cease operations of worker units in radius of influence
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitCapital(Capital capital) {
		// TODO Auto-generated method stub
		visitStructure(capital);
	}

	@Override
	public void visitFarm(Farm farm) {
		// TODO Auto-generated method stub
		visitStructure(farm);
	}

	@Override
	public void visitFort(Fort fort) {
		// attack enemy in radius of influence
		fort.clearQueue();
		// hard coded this shit, 
		fort.addCommand(new AttackCommand(
							PlayerAssetOwnership.getPlayerOwnership(fort),
							map,
							fort,
							t
						));
		fort.addCommand(new AttackCommand(
				PlayerAssetOwnership.getPlayerOwnership(fort),
				map,
				fort,
				t
			));
		fort.addCommand(new AttackCommand(
				PlayerAssetOwnership.getPlayerOwnership(fort),
				map,
				fort,
				t
			));
		fort.addCommand(new AttackCommand(
				PlayerAssetOwnership.getPlayerOwnership(fort),
				map,
				fort,
				t
			));
		fort.addCommand(new AttackCommand(
				PlayerAssetOwnership.getPlayerOwnership(fort),
				map,
				fort,
				t
			));
		// apply defense buff to friendly forces in radius of influence
		visitStructure(fort);
	}

	@Override
	public void visitMine(Mine mine) {
		// TODO Auto-generated method stub
		visitStructure(mine);
	}

	@Override
	public void visitObservationTower(ObservationTower observationTower) {
		// TODO Auto-generated method stub
		visitStructure(observationTower);
	}

	@Override
	public void visitPowerPlant(PowerPlant powerPlant) {
		// TODO Auto-generated method stub
		visitStructure(powerPlant);
	}

	@Override
	public void visitUniversity(University university) {
		// TODO Auto-generated method stub
		visitStructure(university);
	}

	@Override
	public void visitColonist(Colonist colonist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitExplorer(Explorer explorer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitMeleeUnit(MeleeUnit meleeUnit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitRangedUnit(RangedUnit rangedUnit) {
		// TODO Auto-generated method stub
		
	}

}
