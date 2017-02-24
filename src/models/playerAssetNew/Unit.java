package models.playerAssetNew;

import models.visitor.AssetVisitor;

/* High level unit class used to define the Unit type
	and define some basic functionality
 */
public abstract class Unit extends PlayerAsset{

	public Unit(){};
	
    private int movesPerTurn; 	//how many tiles a unit can move in a turn

	//gets unit type, overridden in subclasses
	public String getType(){
    	return "this is a unit";
	}

	public int getMovesPerTurn(){return movesPerTurn;}
	public void setMovesPerTurn(int m){this.movesPerTurn = m;}

    @Override
    public void accept(AssetVisitor v) {
        v.visitUnit(this);
    }
}
