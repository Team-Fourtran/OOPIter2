package models.ctrlCommand;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.AttackCommand;
import models.ctrlCommand.CTRLCommand;
import models.playerAsset.*;

public class CTRLAttackCommand implements CTRLCommand{
    private Player receivingPlayer;
    private CombatAsset giver;  //RallyPoint or Structure
    private TileAssociation receiver;

    public CTRLAttackCommand(CombatAsset giver, TileAssociation receiver, Player receivingPlayer){
        this.receivingPlayer = receivingPlayer;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute(GameMap map, Player player) {
        giver.addCommand(
                new AttackCommand(
                        player,
                        receivingPlayer,
                        map,
                        giver,
                        receiver
                )
        );
//        if (giver instanceof RallyPoint){
//            giver.addCommand(
//                    new AttackCommand(
//                            player,
//                            map,
//                            ((RallyPoint) giver).getArmy(),
//                            receiver
//                    )
//            );
//        }
//        else if (giver instanceof Structure){
//            giver.addCommand(
//                    new AttackCommand(
//                            player,
//                            map,
//                            (Structure) giver,
//                            receiver
//                    )
//            );
//        }
//        else{
//            System.out.println("Can't attack with this");
//        }

    }
}
