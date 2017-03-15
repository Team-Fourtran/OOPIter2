package models.ctrlCommand;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.HarvestCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;

public class CTRLHarvestCommand implements CTRLCommand {
	private ResourceStructure harvester;
	private TileAssociation target;
	private int assignedWorkers;

	private boolean isConfigured;

	public CTRLHarvestCommand() {
		isConfigured = false;
	}

	public void configure(PlayerAsset harvester, TileAssociation target, int assignedWorkers) {
		isConfigured = true;
		this.harvester = (ResourceStructure) harvester;
		this.target = target;
		this.assignedWorkers = assignedWorkers;
	}

	@Override
	public void execute(GameMap map, Player player) throws CommandNotConfiguredException {
		if(isConfigured){
			harvester.addUniversalCommand(
						new HarvestCommand(
								player,
								map,
								harvester,
								target,
								assignedWorkers
								)
						);
		}
	}

}
