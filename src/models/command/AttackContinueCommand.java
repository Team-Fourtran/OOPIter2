package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;
import models.visitor.AttackVisitor;

public class AttackContinueCommand extends AttackCommand {
    private Player givingPlayer;
    private CombatAsset giver;
    private TileAssociation receiver;
    private GameMap map;
    
	public AttackContinueCommand(Player givingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver) {
		super(givingPlayer, map, giver, receiver);
        this.givingPlayer = givingPlayer;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
	}

	@Override
	public void execute() {
        int distance = map.calculateDistance(receiver, giver);
        AttackVisitor v = new AttackVisitor(givingPlayer, map, giver, receiver, distance);
        receiver.accept(v);
        // if the attack visitor notes dead army, then remove universal command
        if (v.isAllDead()) {
        	giver.removeUniversalCommand(this);
        	return;
        }
	}
}
