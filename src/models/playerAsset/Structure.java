package models.playerAsset;

import models.visitor.AssetVisitor;

public class Structure extends PlayerAsset{

    int productionRate;     //turns it takes to create a unit

    public Structure(){

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        productionRate = 2;
        maxHealth = currentHealth = 200;
        upkeep = 1;

    }

    @Override
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }
}
