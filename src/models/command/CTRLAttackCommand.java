package models.command;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.playerAsset.*;

public class CTRLAttackCommand implements Command{
    private Player player;
    private GameMap map;
    private PlayerAsset giver;  //RallyPoint or Structure
    private TileAssociation receiver;

    public CTRLAttackCommand(Player player, GameMap map, PlayerAsset giver, TileAssociation receiver){
        this.player = player;
        this.map = map;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        if (giver instanceof RallyPoint){
            giver.addCommand(
                    new AttackCommand(
                            player,
                            map,
                            ((RallyPoint) giver).getArmy(),
                            receiver
                    )
            );
        }
        else if (giver instanceof Structure){
            giver.addCommand(
                    new AttackCommand(
                            player,
                            map,
                            (Structure) giver,
                            receiver
                    )
            );
        }
        else{
            System.out.println("Can't attack with this");
        }

    }

    @Override
    public double getTurns() {
        return 0;
    }
}
