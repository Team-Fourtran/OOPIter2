package models.playerAsset.Assets.Structures;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

import java.util.ArrayList;
import models.playerAsset.Assets.Technology.*;
import models.visitor.PlayerVisitor;

public class University extends Structure{

    TechFactory factory;
    public University() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(50);
        setMaxHealth(100);
        setCurrentHealth(100);
        setUpkeep(15);
        setRadiusOfInfluence(1);
        setProductionRate(1);
        staff = new ArrayList<>();
        factory = new TechFactory();
    }

    public Technology discoverTechnology(String tech){
        return factory.makeTech(tech);
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

    public String getType(){
        return "university";
    }
}
