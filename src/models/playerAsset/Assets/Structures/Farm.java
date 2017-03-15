package models.playerAsset.Assets.Structures;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;
import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class Farm  extends ResourceStructure{

    double foodProductionMultiplier;
    public Farm() {

        setOffDamage(0);
        setDefDamage(75);
        setArmor(50);
        setMaxHealth(100);
        setCurrentHealth(100);
        setUpkeep(10);
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
