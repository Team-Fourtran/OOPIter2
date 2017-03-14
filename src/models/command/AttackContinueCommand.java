package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;
import models.visitor.AttackVisitor;

public class AttackContinueCommand extends AttackCommand {
    
	public AttackContinueCommand(Player givingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver) {
		super(givingPlayer, map, giver, receiver);
	}

	@Override
	public void execute() {
        int distance = super.getMap().calculateDistance(super.getReceiver(), super.getGiver());
        AttackVisitor v = new AttackVisitor(super.getGivingPlayer(), super.getMap(), super.getGiver(), super.getReceiver(), distance);
        super.getReceiver().accept(v);
        // if the attack visitor notes dead army, then remove universal command
        if (v.isAllDead()) {
        	super.getGiver().removeUniversalCommand(this);
        	return;
        }
	}
}
