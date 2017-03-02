package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Assets.Worker;
import models.tileInfo.Normal;

public class HealingVisitor implements TileVisitor{
    private int productionRate;
    private double basePercentageHealing = .05; //5 percent

    public HealingVisitor(int productionRate){
        this.productionRate = productionRate;
    }

    @Override
    public void visitNormal(Normal normal) {
        //Do nothing
    }

    @Override
    public void visitUnit(Unit unit) {
        int healingFactor = productionRate * (int)(unit.getMaxHealth() * basePercentageHealing);
        unit.incrementHealth(healingFactor);
    }

    @Override
    public void visitArmy(Army army) {
        int healingFactor = productionRate * (int)(army.getMaxHealth() * basePercentageHealing);
        army.incrementHealth(healingFactor);
    }

    @Override
    public void visitStructure(Structure structure) {
        //Structure is doing the healing...
    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {

    }

    @Override
    public void visitWorker(Worker worker) {

    }
}
