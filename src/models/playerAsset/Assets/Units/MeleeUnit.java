package models.playerAsset.Assets.Units;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class MeleeUnit extends Unit{

    public MeleeUnit(){
        setOffDamage(50);
        setDefDamage(50);
        setArmor(50);
        setMaxHealth(75);
        setCurrentHealth(75);
        setUpkeep(25);
        setRadiusOfInfluence(2);
        setMovesPerTurn(.33);
        setVisibility(1);
    }

    public String getType(){
        return "melee";
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitMeleeUnit(this);
        }
        else{
            super.accept(v);
        }
    }
}