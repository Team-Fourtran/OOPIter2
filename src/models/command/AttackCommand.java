package models.command;


import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;

public class AttackCommand implements Command{
    private PlayerAsset giver;
    private TileAssociation receiver;


    public AttackCommand(PlayerAsset giver, TileAssociation receiver){
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {

    }

    @Override
    public double getTurns() {
        return 0;
    }
}
