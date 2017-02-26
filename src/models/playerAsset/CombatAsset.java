package models.playerAsset;

import models.command.Command;
import models.visitor.AssetVisitor;

import java.util.LinkedList;
import java.util.Queue;

public abstract class CombatAsset extends PlayerAsset{

    protected int offDamage;
    protected int defDamage;
    protected int armor;
    protected int maxHealth;
    protected int currentHealth;
    protected int upkeep;
    protected boolean poweredUp;
    protected String assetID;



    //Various getter and setters for attributes
    public void setID(String id){
        assetID = id;
    }
    public String getID(){
        return assetID;
    }
    public int getOffDamage(){
        return offDamage;
    }
    public int getDefDamage(){
        return defDamage;
    }
    public int getArmor(){
        return armor;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
    public boolean getPoweredUp(){
        return poweredUp;
    }
    public int getUpkeep(){
        return upkeep;
    }


    //Power up a unit, increase the resource consumption back to %100
    public void powerUp(){
        if (!poweredUp)
            upkeep *= 4;
    }

    //Power down a powered up unit and change its resource consumption to %25
    public void powerDown(){
        if (poweredUp)
            upkeep /= 4;
    }





}
