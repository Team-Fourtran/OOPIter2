package src.models.playerAsset;

import java.util.*;

/* High level unit class used to define the Unit type
	and define some basic functionality
 */
public abstract class Unit extends PlayerAsset{

    protected int movesPerTurn; 	//how many tiles a unit can move in a turn

    //Heal a unit by 50 health
    public void heal(){
        if (currentHealth + 50 > maxHealth)
            currentHealth = maxHealth;
        else
            currentHealth += 50;
    }

    //gets unit type, overridden in subclasses
    public String getType(){
        return "this is a unit";
    }
}