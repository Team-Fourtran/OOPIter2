package models.command;

import models.assetOwnership.TileAssociation;
import models.playerAsset.Assets.Structures.Structure;
import models.visitor.HealingVisitor;

public class HealCommand implements Command{
    private Structure structure;
    private TileAssociation tileAssociation;

    public HealCommand(Structure structure, TileAssociation tileAssociation){
        this.structure = structure;
        this.tileAssociation = tileAssociation;
    }

    @Override
    public void execute() {
        tileAssociation.accept(
                new HealingVisitor(structure.getProductionRate())
        );
    }

    @Override
    public double getTurns() {
        //TODO: figure out how to continue until assets are fully healed
        return 1;
    }
}
