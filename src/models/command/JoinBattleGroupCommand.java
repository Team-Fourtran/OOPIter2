package models.command;

import models.assetOwnership.GameMap;
import models.playerAsset.Assets.Army;
import models.playerAsset.Assets.Units.Unit;

public class JoinBattleGroupCommand implements Command{
    private Army army;
    private Unit unit;
    private GameMap map;

    public JoinBattleGroupCommand(Army army, Unit unit, GameMap map){
        this.army = army;
        this.unit = unit;
        this.map = map;
    }

    @Override
    public void execute() {
    	System.out.println("?");
        if (army == null || !army.includes(unit) || !map.assetExists(unit)){
            army.removeUniversalCommand(this);
            return;
        }
        if ( map.calculateDistance(army, unit) == 0 ){
            army.addToBattleGroup(unit);
            // recalculate RoI through the map
            map.updateInfluenceRadius(army);
            map.removeAssetFromMap(unit);
            army.removeUniversalCommand(this);
            return;
        }
    }

    @Override
    public double getTurns() {
        return 0;
    }
}
