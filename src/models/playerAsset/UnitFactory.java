package models.playerAsset;

public class UnitFactory {

    //factory method to make any of the four units
    public Unit makeUnit(String type){
        switch (type){
            case "explorer":
                    return new Explorer();
            case "colonist":
                return new Colonist();
        }
        return null;
    }

}
