package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Structures.ResourceStructure;

public class HarvestCommand implements Command {
	private Player givingPlayer;
	private ResourceStructure harvester;
	private TileAssociation target;
	private GameMap map;
	private int assignedWorkers;
	private boolean firstExecution;

	public HarvestCommand(Player givingPlayer, GameMap map, ResourceStructure harvester, TileAssociation target, int assignedWorkers) {
		this.givingPlayer = givingPlayer;
		this.map = map;
		this.harvester = harvester;
		this.target = target;
		this.assignedWorkers = assignedWorkers;
		this.firstExecution = true;
	}
	
	@Override
	public void execute() {
		if (firstExecution) {
			if (assignedWorkers != 0) {
				harvester.addWorkersToGathering(assignedWorkers);
				firstExecution = false;
			}
			else {
				harvester.ceaseHarvesting();
				harvester.removeUniversalCommand(this);
			}
		}
		int harvest = harvester.harvest(target);
		if ((harvester == null) || (harvest <= 0)) {
			harvester.removeUniversalCommand(this);
		}
		// there needs to be a universal command so the structure continuously harvests
		// keep executing until it is depleted!!
	}

	@Override
	public double getTurns() {
		return 2;
	}

}
