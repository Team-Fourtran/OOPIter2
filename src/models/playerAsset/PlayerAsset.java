package models.playerAsset;

import models.visitor.AssetVisitor;

public abstract class PlayerAsset {

    public String getType(){
        return "basic asset type";
    }
    public abstract void accept(AssetVisitor v);

}
