package models.playerAsset.Assets.Structures;

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

    public void assignWorkers(ArrayList<Worker> workers){
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

    public ArrayList<Worker> acquireWorkers(int num){
        ArrayList<Worker> list = new ArrayList<>();
        if(staff.isEmpty()){
            return null;
        }
        else if (num > staff.size()){
            for(int i = 0; i < staff.size(); i++){
                list.addAll(staff);
            }
        }
        else{
            for(int i = 0; i < num; i++){
                list.add(staff.remove(0));
            }
        }
        return list;
    }

    public int getNumWorkers(){
        return staff.size();
    }
}
