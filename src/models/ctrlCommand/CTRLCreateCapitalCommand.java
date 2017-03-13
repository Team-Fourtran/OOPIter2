package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.Units.Colonist;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.CapitalCreationVisitor;

public class CTRLCreateCapitalCommand implements CTRLCommand{
    private Unit unit;  //TODO: Might be able to make of type Colonist

    private boolean isConfigured;

    public CTRLCreateCapitalCommand(){
        isConfigured = false;
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.unit = (Unit) parts.getRequestingAsset();
        isConfigured = true;
        parts.requestExecution();
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            if(unit instanceof Colonist){
                player.accept(
                        new CapitalCreationVisitor(
                                unit,
                                map
                        )
                );
            }
            else{
                System.out.println("Need Colonist to create capital!");
            }
        } else{
            throw new CommandNotConfiguredException("[" + this + "] is not configured.");
        }
    }

    @Override
    public String toString(){
        return "Create Capital";
    }

    @Override
    public void queryAgain() throws CommandNotConfiguredException {
        //Unused
    }
}
