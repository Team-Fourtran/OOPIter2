package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.PlayerAssetOwnership;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

public class AttackVisitor implements AssetVisitor{
    private Player givingPlayer;
    private GameMap map;
    private CombatAsset giver;
    private TileAssociation receiver;
    private int distance;

    public AttackVisitor(Player givingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver, int distance){
        this.givingPlayer = givingPlayer;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
        this.distance = distance;
    }

    @Override
    public void visitUnit(Unit unit) {
    	// check if unit is not of the giving player
    	if (PlayerAssetOwnership.getPlayerOwnership(unit) != givingPlayer) {
            //unit is not part of an army
            unit.depleteHealth(giver.getOffDamage(distance));

            if ( unit.getCurrentHealth() <= 0 ){
                //Dead!
                unit.accept(
                        new DeathVisitor(map, PlayerAssetOwnership.getPlayerOwnership(unit))
                );
                giver.clearQueue();
                //TODO: EXPLOSION
            }
            else{
                //Still alive!
                //Won't reciprocate b/c not in an army
            }
    	}
    }
    @Override
    public void visitStructure(Structure structure) {
    	if (PlayerAssetOwnership.getPlayerOwnership(structure) != givingPlayer) {
	        structure.depleteHealth(giver.getOffDamage(distance));
	
	        if ( structure.getCurrentHealth() <= 0 ){
	            //Structure is Dead!
	            structure.accept(
	                    new DeathVisitor(map, PlayerAssetOwnership.getPlayerOwnership(structure))
	            );
	            giver.clearQueue();
	            //TODO: EXPLOSION
	        }
	        else{
	            //Still alive!
	            //For defDamage allocation:
	            if (structure.getDefDamage(distance) > 0){
	                giver.depleteHealth(structure.getDefDamage(distance));
	                if (giver.getCurrentHealth() <= 0 ){
	                    //Dead!
	                    giver.accept(
	                            new DeathVisitor(map, givingPlayer)
	                    );
	                }
	            }
	        }
    	}
    }

    @Override
    public void visitArmy(Army army) {
    	// check if unit is not of the giving player
    	if (PlayerAssetOwnership.getPlayerOwnership(army) != givingPlayer) {
            //unit is not part of an army
            army.depleteHealth(giver.getOffDamage(distance));
            if ( army.getCurrentHealth() <= 0 ){
                //Dead!
                army.accept(
                        new DeathVisitor(map, PlayerAssetOwnership.getPlayerOwnership(army))
                );
                giver.clearQueue();
                //TODO: EXPLOSION
            }
            else{
                //Still alive!
	            //For defDamage allocation:
	            if (army.getDefDamage(distance) > 0){
	                giver.depleteHealth(army.getDefDamage(distance));
	                if (giver.getCurrentHealth() <= 0 ){
	                    //Dead!
	                    giver.accept(
	                            new DeathVisitor(map, givingPlayer)
	                    );
	                }
	            }
            }
    	}
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {
    }
    
    public boolean isAllDead() {
    	// need to check if there are any enemies left in TA asset Owner
    	for (int i = 0; i < receiver.getAssetOwner().getAssets().size(); i++) {
    		PlayerAsset p = receiver.getAssetOwner().getAssets().get(i);
    		// if there is another player asset in this tile association that is not of the player, continue
    		if (PlayerAssetOwnership.getPlayerOwnership(p) != givingPlayer) {
    			return true;
    		}
    	}
    	return false;
    }
}
