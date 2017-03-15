package models.playerAsset.Assets.Units;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

//Unit type
//Responsibilities: Attacker, can attack over multiple tiles
public class RangedUnit extends Unit{

    public RangedUnit(){
        setOffDamage(50);
        setDefDamage(25);
        setArmor(50);
        setMaxHealth(60);
        setCurrentHealth(60);
        setUpkeep(25);
        setRadiusOfInfluence(3);
        setMovesPerTurn(.33);
    }
    public String getType(){
        return "ranged";
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitRangedUnit(this);
        }
        else{
            super.accept(v);
        }
    }
}