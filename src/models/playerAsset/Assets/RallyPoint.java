package models.playerAsset.Assets;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.NonCombatAsset;
import models.visitor.AssetVisitor;

public class RallyPoint extends NonCombatAsset {
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
