package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;
import models.visitor.AttackVisitor;
import models.playerAsset.Assets.PlayerAsset;


public class AttackCommand implements Command{
    private Player givingPlayer;
    private CombatAsset giver;
    private TileAssociation receiver;
    private GameMap map;


    public AttackCommand(Player givingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver){
        this.givingPlayer = givingPlayer;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        int distance = map.calculateDistance(receiver, giver);
        receiver.accept(
            new AttackVisitor(
                    givingPlayer,
                    map,
                    giver,
                    receiver,
                    distance
            )
        );
    }

    @Override
    public double getTurns() {
        return 1;
    }
}
