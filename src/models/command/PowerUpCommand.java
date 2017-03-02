package models.command;


import models.playerAsset.Assets.CombatAsset;

public class PowerUpCommand implements Command{
    private CombatAsset asset;

    public PowerUpCommand(CombatAsset asset){
        this.asset = asset;
    }

    @Override
    public void execute() {
        asset.powerUp();
    }

    @Override
    public double getTurns() {
        return 1;
    }
}
