package models.command;


import models.playerAsset.Assets.CombatAsset;

public class PowerDownCommand implements Command{
    private CombatAsset asset;

    public PowerDownCommand(CombatAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute() {
        asset.powerDown();
    }

    @Override
    public double getTurns() {
        return 1;
    }
}
