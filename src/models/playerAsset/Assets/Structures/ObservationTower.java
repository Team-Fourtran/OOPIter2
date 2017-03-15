package models.playerAsset.Assets.Structures;

import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

public class ObservationTower extends Structure {
    public ObservationTower() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(2);
        setProductionRate(1);
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
