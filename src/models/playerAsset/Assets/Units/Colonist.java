package models.playerAsset.Assets.Units;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

//Unit type
//Responsibilities: Weak attacker, can be consumed to make structure
public class Colonist extends Unit{

    public Colonist(){
        setOffDamage(0);
        setDefDamage(0);
        setArmor(10);
        setMaxHealth(50);
        setCurrentHealth(50);
        setUpkeep(15);
        setRadiusOfInfluence(1);
        setMovesPerTurn(.33);

    }

    public String getType(){
        return "colonist";
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitColonist(this);
        }
        else{
            super.accept(v);
        }
    }
}
