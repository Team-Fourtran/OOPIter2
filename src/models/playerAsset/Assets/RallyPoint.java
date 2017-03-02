package models.playerAsset.Assets;

import models.command.Command;
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

    //Overrides to redirect to battle group
    public void addCommand(Command c) {
        army.addCommand(c);
    }
}
