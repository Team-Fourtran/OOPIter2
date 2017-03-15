package models.playerAsset.Assets.Structures;

import models.assetOwnership.Radius;
import models.playerAsset.Assets.CombatAsset;
import models.playerAsset.Assets.Worker;
import models.visitor.AssetVisitor;
import java.util.ArrayList;

public class Structure extends CombatAsset {

    int productionRate;     //turns it takes to create a unit
    ArrayList<Worker> staff;

    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }

    public String getType(){
        return "Basic structure";
    }
    
    public void setProductionRate(int productionRate){this.productionRate = productionRate;}

    public int getProductionRate(){
        return productionRate;
    }

    public void assignWorkers(Worker... workers){
        for (Worker w: workers) {
            staff.add(w);
            productionRate += w.getProduction();
        }
    }

    public void removeWorkers(Worker... workers){
        for (Worker w: workers) {
            staff.remove(w);
            productionRate -= w.getProduction();
        }
    }
}
