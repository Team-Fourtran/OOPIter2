package models.playerAssetNew;

import models.visitor.AssetVisitor;

//Unit type
//Responsibilities: Weak attacker, can be consumed to make structure
public class Colonist extends Unit{

    public String getType(){
        return "Colonist";
    }
}
