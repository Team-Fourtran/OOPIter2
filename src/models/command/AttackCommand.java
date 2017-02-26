package models.command;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.CombatAsset;
import models.playerAsset.Player;
import models.playerAsset.PlayerAsset;
import models.visitor.AttackVisitor;

public class AttackCommand implements Command{
    private Player player;
    private CombatAsset giver;
    private TileAssociation receiver;
    private GameMap map;


    public AttackCommand(Player player, GameMap map, CombatAsset giver, TileAssociation receiver){
        this.player = player;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.accept(
            new AttackVisitor(
                    player,
                    map,
                    giver,
                    receiver
            )
        );
    }

    @Override
    public double getTurns() {
        return 1;
    }
}
