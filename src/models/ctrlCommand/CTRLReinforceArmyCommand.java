package models.ctrlCommand;

import controllers.CommandComponents;
import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Units.Unit;
import models.visitor.ReinforceArmyVisitor;

public class CTRLReinforceArmyCommand implements CTRLCommand{
    private CommandComponents parts;
    private Unit unit;
    private RallyPoint rallyPoint;

    private boolean isConfigured;

    public CTRLReinforceArmyCommand(){
        isConfigured = false;
    }

    public void configure(Unit unit, RallyPoint rallyPoint){
        this.isConfigured = true;
        this.unit = unit;
        this.rallyPoint = rallyPoint;
    }

    @Override
    public void callback() throws CommandNotConfiguredException {
        this.rallyPoint = (RallyPoint) parts.getTargetAsset();
        if(null != rallyPoint){
            isConfigured = true;
            parts.requestExecution();   //Request execution
        } else {
            throw new CommandNotConfiguredException("Well, Shit");
        }
    }

    @Override
    public void configure(CommandComponents parts) throws CommandNotConfiguredException {
        this.parts = parts;
        this.unit = (Unit) parts.getRequestingAsset();
        parts.requestDestinationRallypoint(this);
        isConfigured = false;
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
