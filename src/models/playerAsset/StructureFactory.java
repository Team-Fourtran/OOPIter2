package models.playerAsset;

import models.playerAsset.MeleeUnit;
import models.playerAsset.MeleeUnit;

public class StructureFactory {

    //factory method to make any of the four units
    public Structure makeStructure(String type){
        switch (type){
            case "base":
                return new Base();
            /*
            case "farm":
                return new Farm();
            case "mine":
                return new Mine();
            case "power plant":
                return new PowerPlant();
            case "fort":
                return new Fort();
            case "observation tower":
                return new ObservationTower();
            case "university":
                return new University();
            */
        }

        return null;
    }
}
