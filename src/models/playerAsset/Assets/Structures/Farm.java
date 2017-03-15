package models.playerAsset.Assets.Structures;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Farm  extends ResourceStructure{

    int rawMaterial;
    int producedMaterial;
    double foodProductionMultiplier;

    public Farm() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(1);
        setProductionRate(0);
        setWorkerDensity(10);
        foodProductionMultiplier = 1;
        staff = new ArrayList<>();
        gatherers = new ArrayList<>();
        producers = new ArrayList<>();
    }

    public void increaseProduction(String type, double i){
        if (type.equals("food"))
            foodProductionMultiplier += i;
    }

    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitFarm(this);
        }
        else{
            super.accept(v);
        }
    }


    public String getType(){
        return "farm";
    }

    public void printStats(){
        super.printStats();
        System.out.println(foodProductionMultiplier);
    }

}
