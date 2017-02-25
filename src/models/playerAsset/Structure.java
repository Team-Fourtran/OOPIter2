package models.playerAsset;

import models.visitor.AssetVisitor;

/* Class representing the PlayerAsset Structure
   At this time, only represents a base
 */
public class Structure extends PlayerAsset{

    int productionRate;     //turns it takes to create a unit

    public Structure(){
    }

    @Override
    public void accept(AssetVisitor v) {
        v.visitStructure(this);
    }
}
