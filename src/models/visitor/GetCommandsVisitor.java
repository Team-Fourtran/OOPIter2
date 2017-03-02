package models.visitor;

import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;

public class GetCommandsVisitor implements AssetVisitor{

    public GetCommandsVisitor(){

    }

    @Override
    public void visitUnit(Unit unit) {

    }

    @Override
    public void visitArmy(Army army) {

    }

    @Override
    public void visitStructure(Structure structure) {

    }

    @Override
    public void visitRallyPoint(RallyPoint rallyPoint) {

    }
}
