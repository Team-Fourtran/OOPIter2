package models.playerAssetNew;

import models.visitor.TileVisitor;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class Explorer extends Unit {

    public String getType(){
        return "Explorer";
    }
}
