package models.command;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.CombatAsset;
import models.playerAsset.Player;
import models.playerAsset.PlayerAsset;

public class CTRLAttackCommand implements Command{
    private Player player;
    private GameMap map;
    private CombatAsset giver;
    private TileAssociation receiver;

    public CTRLAttackCommand(Player player, GameMap map, CombatAsset giver, TileAssociation receiver){
        this.player = player;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        giver.addCommand(
                new AttackCommand(
                        player,
                        map,
                        giver,
                        receiver
                )
        );
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
