package models.tileInfo;


import models.visitor.TileVisitor;

public class Decal implements Item {
	String decalType;

	// Create a decal and specify type with a string. Must be either "crossBones" or "redCross"
	public Decal(String decalType) {
		switch(decalType) {
		case "crossBones": 	this.decalType = decalType;
							break;
		case "redCross": 	this.decalType = decalType;
							break;
		default:			throw new IllegalArgumentException();
		}
	}
	
	@Override
	// Decal items do not have any effects
	public double getItemEffect() {
		return 0;
	}

	@Override
	public void accept(TileVisitor v) {
		//TODO
	}

	// Returns String representing decal type, i.e., crossBones, redCross
	public String getDecalType() {
		return decalType;
	}

}
