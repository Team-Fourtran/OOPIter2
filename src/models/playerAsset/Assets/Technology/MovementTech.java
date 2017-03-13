package models.playerAsset.Assets.Technology;

import models.playerAsset.Assets.CombatAsset;

/**
 * Created by Clay on 3/2/2017.
 */

//should be in separate abstract class because it doesnt apply to structures
public class MovementTech extends StatTechnology {

    public void apply(CombatAsset a){
        a.setMovementTurns(a.getMovementTurns() / 2);
    }
}
