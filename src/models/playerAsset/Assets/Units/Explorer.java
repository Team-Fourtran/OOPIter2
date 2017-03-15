package models.playerAsset.Assets.Units;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class Explorer extends Unit{

    public Explorer(){
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(2);
        setMovesPerTurn(.33);
    }

    public String getType(){
        return "explorer";
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitExplorer(this);
        }
        else{
            super.accept(v);
        }
    }
}
