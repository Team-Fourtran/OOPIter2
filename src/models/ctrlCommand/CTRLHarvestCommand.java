package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.HarvestCommand;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.PlayerAsset;
import models.playerAsset.Assets.Structures.ResourceStructure;
import models.playerAsset.Assets.Structures.Structure;

public class CTRLHarvestCommand implements CTRLCommand {
	private CommandComponents parts;
	private ResourceStructure harvester;
	private TileAssociation target;
	private int assignedWorkers;

	private boolean isConfigured;

	@Override
	public boolean isConfigured(){
		return this.isConfigured;
	}

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
	public void configure(CommandComponents parts) throws CommandNotConfiguredException {
	    this.parts = parts;
		this.harvester = (ResourceStructure)parts.getRequestingAsset();
		this.assignedWorkers = parts.getNumWorkers();
		parts.requestDestinationTile(this);
		isConfigured = false;
	}

	@Override
	public void callback() throws CommandNotConfiguredException {
        this.target = parts.getDestinationTile(); //Query parts for the destination tile.
        if(null != target){
            isConfigured = true;
            parts.requestExecution();   //Request execution
        } else {
            throw new CommandNotConfiguredException("damn");
        }
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
    @Override
    public String toString(){
	    return "Harvest";
    }
}
