package models.playerAsset.Assets.Structures;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

import java.util.ArrayList;

public class University extends Structure{

    public University() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(1);
        setProductionRate(1);
        staff = new ArrayList<>();
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitUniversity(this);
        }
        else{
            super.accept(v);
        }
    }
}
