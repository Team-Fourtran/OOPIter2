package models.ctrlCommand;


import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.AttackCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLAttackCommand implements CTRLCommand{
    private Player receivingPlayer;
    private CombatAsset giver;  //RallyPoint or Structure
    private TileAssociation receiver;

    private boolean isConfigured;

    public CTRLAttackCommand(){
        isConfigured = false;
    }

    public void configure(CombatAsset giver, TileAssociation receiver, Player receivingPlayer){
        isConfigured = true;
        this.receivingPlayer = receivingPlayer;
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {

    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            giver.addCommand(
                    new AttackCommand(
                            player,
                            receivingPlayer,
                            map,
                            giver,
                            receiver
                    )
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }

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
