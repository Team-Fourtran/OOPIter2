package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;
import java.util.ArrayList;

public class PowerPlant extends ResourceStructure {

    int rawMaterial;
    int producedMaterial;
    double energyProductionMultiplier;

    public PowerPlant() {

        setOffDamage(0);
        setDefDamage(75);
        setArmor(50);
        setMaxHealth(100);
        setCurrentHealth(100);
        setUpkeep(10);
        setRadiusOfInfluence(1);
        setWorkerDensity(10);
        energyProductionMultiplier = 1;
    }

    public void increaseProduction(String type, double i){
        if (type.equals("energy"))
            energyProductionMultiplier += i;
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitPowerPlant(this);
        }
        else{
            super.accept(v);
        }
    }

    public String getType(){
        return "powerplant";
    }

    public void printStats(){
        super.printStats();
        System.out.println(energyProductionMultiplier);
    }

}
