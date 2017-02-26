package models.command;


import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.PlayerAsset;

public class CTRLAttackCommand implements Command{
    private PlayerAsset giver;
    private TileAssociation receiver;

    public CTRLAttackCommand(PlayerAsset giver, TileAssociation receiver){
        this.giver = giver;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        giver.addCommand(
                new AttackCommand(
                        giver,
                        receiver
                )
        );
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
