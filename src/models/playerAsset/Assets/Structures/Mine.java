package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;
import java.util.ArrayList;

public class Mine extends ResourceStructure{

    double oreProductionMultiplier;

    public Mine() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(1);
        setProductionRate(1);
        setWorkerDensity(1);

        oreProductionMultiplier = 1;
        staff = new ArrayList<>();
        gatherers = new ArrayList<>();
        producers = new ArrayList<>();
    }

    public void increaseProduction(String type, double i){
        if (type.equals("ore"))
            oreProductionMultiplier += i;
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor){
            ((SpecificAssetVisitor)v).visitMine(this);
        }
        else{
            super.accept(v);
        }
    }

    public String getType(){
        return "mine";
    }

    public void printStats(){
        super.printStats();
        System.out.println(oreProductionMultiplier);
    }

}
