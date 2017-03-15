package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Units.Unit;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

import java.util.ArrayList;

public class Fort extends Structure{

    private ArrayList<Unit> trainers;

    public Fort() {

        setOffDamage(100);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(20);
        setRadiusOfInfluence(2);
        trainers = new ArrayList<>();
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitFort(this);
        }
        else{
            super.accept(v);
        }
    }

    public String getType(){
        return "fort";
    }
}
