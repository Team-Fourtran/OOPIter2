package models.playerAssetNew;

import models.visitor.AssetVisitor;

public class RallyPoint extends PlayerAsset{
    private Army army;

    public void accept(AssetVisitor v){
        v.visitRallyPoint(this);
        army.accept(v);
    }

    public void setArmy(Army a){
        this.army = a;
    }
}
