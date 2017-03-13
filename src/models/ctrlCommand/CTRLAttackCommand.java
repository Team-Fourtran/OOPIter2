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
    private CommandComponents parts;

    public CTRLAttackCommand(){
        isConfigured = false;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.receivingPlayer = parts.getOpposingPlayer();
        this.giver = (CombatAsset) parts.getRequestingAsset();
        parts.requestDestinationTile();
        //Still not configured - queryAgain needs to be called once the destination tile is ready.
    }

    public void queryAgain() throws CommandNotConfiguredException{
        this.receiver = parts.getDestinationTile();
        if(null != receiver){
            isConfigured = true;
            parts.requestExecution();
        } else {
            throw new CommandNotConfiguredException("queryAgain() was called, but the DestinationTile is null");
        }
    }

    public boolean isConfigured(){
        return this.isConfigured;
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

    @Override
    public String toString(){
        return "Attack";
    }
}
