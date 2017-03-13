package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.ReinforceArmyVisitor;

public class CTRLReinforceArmyCommand implements CTRLCommand{
    private Unit unit;
    private RallyPoint rallyPoint;

    private boolean isConfigured;

    public CTRLReinforceArmyCommand(){
        isConfigured = false;
    }

//    public void configure(Unit unit, RallyPoint rallyPoint){
//        this.isConfigured = true;
//        this.unit = unit;
//        this.rallyPoint = rallyPoint;
//    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
    }

    @Override
    //Wait for RallyPoint to become available to query
    public void queryAgain() throws CommandNotConfiguredException {
        isConfigured = true;
    }

    public boolean isConfigured(){
        return this.isConfigured;
    }

    @Override
    public void execute(GameMap map, Player player) throws CommandNotConfiguredException{
        if(isConfigured){
            rallyPoint.accept(
                    new ReinforceArmyVisitor(map, player, unit)
            );
        } else throw new CommandNotConfiguredException("[" + this + "] is not configured.");
    }

    @Override
    public String toString(){
        return "Reinforce Army";
    }
}
