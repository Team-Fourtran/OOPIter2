package models.visitor;

import models.playerAsset.Assets.ArmyManager;
import models.playerAsset.Assets.Player;
import models.playerAsset.Assets.StructureManager;
import models.playerAsset.Assets.Structures.University;
import models.playerAsset.Assets.Technology.Technology;
import models.playerAsset.Assets.UnitManager;

public class NewTechVisitor implements PlayerVisitor{

    private Technology tech;
    private String type, asset;
    private University univ;

    public NewTechVisitor(String type, String asset, University univ){
        this.type = type;
        this.asset = asset;
        this.univ = univ;
    }
    public void visitPlayer(Player player){}
    public void visitArmyManager(ArmyManager armyManager){}

    public void visitStructureManager(StructureManager structureManager){
        tech = univ.discoverTechnology(type);
        structureManager.addTech(asset, tech);

    }

    public void visitUnitManager(UnitManager unitManager){
        tech = univ.discoverTechnology(type);
        unitManager.addTech(asset, tech);
    }
}
