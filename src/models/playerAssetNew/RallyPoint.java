package models.playerAssetNew;

import models.visitor.AssetVisitor;
import models.visitor.TileVisitor;

public class RallyPoint extends PlayerAsset{
    private Army army;

    public void accept(AssetVisitor v){
        v.visitRallyPoint(this);
        army.accept(v);
    }

    public void setArmy(Army a){
        this.army = a;
    }

	@Override
	public void accept(TileVisitor v) {
		// TODO Auto-generated method stub
		
	}
}
