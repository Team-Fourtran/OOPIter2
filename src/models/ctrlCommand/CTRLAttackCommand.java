package models.ctrlCommand;


import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.AttackCommand;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Player;

public class CTRLAttackCommand implements CTRLCommand{
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
        this.giver = (CombatAsset) parts.getRequestingAsset();
        parts.requestDestinationTile(this);
        //Still not configured - queryAgain needs to be called once the destination tile is ready.
    }

    //Called back when getDestinationTile is ready
    public void callback() throws CommandNotConfiguredException{
        this.receiver = parts.getDestinationTile(); //Query parts for the destination tile.
        if(null != receiver){       //Calling requestDestinationTile set it to null before initiating the highlighting
            //If it's not null, highlighting worked properly and we have a DestinationTile
            isConfigured = true;    //Flip the flag so that it'll execute properly without exceptions
            parts.requestExecution();   //Request execution
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
                            map,
                            giver,
                            receiver
                    )
            );
        } else {
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Attack";
    }
}
