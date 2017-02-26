package models.playerAsset;

import models.visitor.AssetVisitor;
import models.visitor.TileVisitor;

public class RallyPoint extends NonCombatAsset{
    private Army army;

    public void accept(AssetVisitor v){
        v.visitRallyPoint(this);
        //army.accept(v);
    }

    public void setArmy(Army a){
        this.army = a;
    }

    public Army getArmy(){
        return this.army;
    }
}
