package models.playerAsset.Assets;

<<<<<<< HEAD:src/models/playerAsset/UnitFactory.java
=======
import models.playerAsset.Assets.Units.*;

>>>>>>> master:src/models/playerAsset/Assets/UnitFactory.java
public class UnitFactory {

    //factory method to make any of the four units
    public Unit makeUnit(String type){
        switch (type){
            case "explorer":
                return new Explorer();
            case "colonist":
                return new Colonist();
            case "melee":
                return new MeleeUnit();
            case "ranged":
                return new RangedUnit();

        }

        return null;
    }
}
