package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;
import models.visitor.AssetVisitor;
import models.visitor.SpecificAssetVisitor;

import java.util.ArrayList;

public class Capital extends ResourceStructure {


    double foodProductionMultiplier;
    double oreProductionMultiplier;
    double energyProductionMultiplier;

    public Capital() {
        setOffDamage(0);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(30);
        setRadiusOfInfluence(2);
        setWorkerDensity(10);

        foodProductionMultiplier = 1;
        oreProductionMultiplier = 1;
        energyProductionMultiplier = 1;
        // Important: need to set harvest strategy of capital before it can gather resources
        //TODO: this needs to go through worker manager
        for (int i = 0; i < 5; i++) {
            Worker w = new Worker();
            staff.add(w);
            productionRate += w.getProduction();
        }
    }
    public void increaseProduction(String type, double i){
        switch (type){
            case "ore": oreProductionMultiplier += i; break;
            case "food": foodProductionMultiplier += i; break;
            case "energy": energyProductionMultiplier += i; break;
        }
    }

    public String getType() {
        return "capital";
    }

    @Override
    public void accept(AssetVisitor v) {
        if (v instanceof SpecificAssetVisitor) {
            ((SpecificAssetVisitor) v).visitCapital(this);
        } else {
            super.accept(v);
        }
    }

    public void printStats(){
        super.printStats();
        System.out.println(oreProductionMultiplier);
        System.out.println(foodProductionMultiplier);
        System.out.println(energyProductionMultiplier);


    }
}
