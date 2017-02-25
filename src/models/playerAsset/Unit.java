package models.playerAsset;

import models.visitor.AssetVisitor;

/* High level unit class used to define the Unit type
	and define some basic functionality
 */
public abstract class Unit extends PlayerAsset{
	
    private double movesPerTurn; 	//REVISIT AND MODIFY

	//gets unit type, overridden in subclasses
	public String getType(){
    	return "this is a unit";
	}

	public double getMovesPerTurn(){return movesPerTurn;}
	public void setMovesPerTurn(double m){this.movesPerTurn = m;}

    @Override
    public void accept(AssetVisitor v) {
        v.visitUnit(this);
    }
}

