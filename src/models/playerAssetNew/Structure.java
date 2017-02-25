package models.playerAssetNew;

import models.visitor.AssetVisitor;
import models.visitor.TileVisitor;

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

	@Override
	public void accept(TileVisitor v) {
		v.visitStructure(this);
		
	}
}
