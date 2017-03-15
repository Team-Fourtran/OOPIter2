package models.playerAsset.Assets.Units;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class Explorer extends Unit{

    public Explorer(){
        setOffDamage(10);
        setDefDamage(10);
        setArmor(50);
        setMaxHealth(50);
        setCurrentHealth(50);
        setUpkeep(15);
        setRadiusOfInfluence(2);
        setMovesPerTurn(.33);
        setVisibility(2);
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
