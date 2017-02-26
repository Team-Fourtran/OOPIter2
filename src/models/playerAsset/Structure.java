package models.playerAsset;

import models.visitor.AssetVisitor;

import java.util.ArrayList;

public class Structure extends CombatAsset{

    int productionRate;     //turns it takes to create a unit
    ArrayList<Worker> staff;

    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }

    public String getType(){
        return "Basic structure";
    }
}
