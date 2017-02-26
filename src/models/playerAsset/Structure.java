package models.playerAsset;

import models.visitor.AssetVisitor;

<<<<<<< HEAD
import java.util.ArrayList;

public class Structure extends CombatAsset{
=======
public abstract class Structure extends PlayerAsset{
>>>>>>> master

    int productionRate;     //turns it takes to create a unit
    ArrayList<Worker> staff;

<<<<<<< HEAD
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }

    public String getType(){
        return "Basic structure";
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
=======
    @Override
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
>>>>>>> master
    }
}
