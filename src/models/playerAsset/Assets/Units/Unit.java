package models.playerAsset.Assets.Units;

import models.playerAsset.Assets.AssetObserver;
import models.playerAsset.Assets.CombatAsset;
import java.util.Observable;
import java.util.Vector;

import models.visitor.AssetVisitor;

/* High level unit class used to define the Unit type
	and define some basic functionality
 */
public abstract class Unit extends CombatAsset{
	
    private double movesPerTurn; 	//REVISIT AND MODIFY

	//gets unit type, overridden in subclasses
	public String getType(){
    	return "this is a unit";
	}

	public double getMovesPerTurn(){return movesPerTurn;}

	public void setMovesPerTurn(double m){this.movesPerTurn = m;}

    public void accept(AssetVisitor v) {
        v.visitUnit(this);
    }
}

