package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.CombatAsset;
import models.playerAsset.Player;
import models.visitor.AttackVisitor;

public class AttackCommand implements Command{
    private Player givingPlayer;
    private Player receivingPlayer;
    private CombatAsset giver;
    private TileAssociation receiver;
    private GameMap map;


    public AttackCommand(Player givingPlayer, Player receivingPlayer, GameMap map, CombatAsset giver, TileAssociation receiver){
        this.givingPlayer = givingPlayer;
        this.receivingPlayer = receivingPlayer;
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
                    receivingPlayer,
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
