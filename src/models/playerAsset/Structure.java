package models.playerAsset;

import models.visitor.AssetVisitor;

public class Structure extends PlayerAsset{

    int productionRate;     //turns it takes to create a unit

    @Override
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }

    public String getType(){
        return "Basic structure";
    }
}
