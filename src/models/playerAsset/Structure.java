package models.playerAsset;

import models.visitor.AssetVisitor;

public abstract class Structure extends PlayerAsset{

    int productionRate;     //turns it takes to create a unit

    @Override
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }
}
