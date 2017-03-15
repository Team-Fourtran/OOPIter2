package models.playerAsset.Assets.Structures;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

public class ObservationTower extends Structure {
    public ObservationTower() {

        setOffDamage(0);
        setDefDamage(75);
        setArmor(100);
        setMaxHealth(100);
        setCurrentHealth(100);
        setUpkeep(10);
        setRadiusOfInfluence(2);
    }


    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitObservationTower(this);
        }
        else{
            super.accept(v);
        }
    }

    public String getType(){
        return "observationtower";
    }
}
