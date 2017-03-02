package models.visitor;

import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.JoinBattleGroupCommand;
import models.playerAsset.Assets.*;
import models.playerAsset.Assets.Units.Unit;

public class ArmyCreationVisitor implements PlayerVisitor{

    private TileAssociation location;
    private Unit[] units;
    private GameMap map;

    public ArmyCreationVisitor(GameMap map, TileAssociation location, Unit ... units){
        this.location = location;
        this.units = units;
        this.map = map;
    }

    @Override
    public void visitPlayer(Player player) {
        this.visitArmyManager(player.getArmies());
        this.visitUnitManager(player.getUnits());
        this.visitStructureManager(player.getStructures());
    }

    @Override
    public void visitArmyManager(ArmyManager armyManager) {

        //Should have Army Factory to return new army...
        Army newArmy = armyManager.formArmy(units);
        RallyPoint rp = armyManager.formRallyPoint(newArmy);

        rp.setArmy(newArmy);
        location.add(rp);
        location.add(newArmy); //Do we need to add the army?
        for (Unit u : units){
            newArmy.addUniversalCommand(
                    new JoinBattleGroupCommand(newArmy, u, map)
            );
        }
        rp.accept(
                new MovementVisitor(map, location)
        );

    }

    @Override
    public void visitStructureManager(StructureManager structureManager) {
        //nothing
    }

    @Override
    public void visitUnitManager(UnitManager unitManager) {
        //TODO Ask Clay what happens to the units here that are added to an army
    }
}
