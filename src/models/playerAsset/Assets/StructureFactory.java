package models.playerAsset.Assets;

import models.playerAsset.Assets.Structures.*;

public class StructureFactory {

    //factory method to make any of the four units
    public Structure makeStructure(String type){
        switch (type){
            case "capital":
                return new Capital();
            case "farm":
                return new Farm();
            case "mine":
                return new Mine();
            case "powerplant":
                return new PowerPlant();
            case "fort":
                return new Fort();
            case "observationtower":
                return new ObservationTower();
            case "university":
                return new University();
        }

        return null;
    }
}

