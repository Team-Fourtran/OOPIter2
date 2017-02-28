package models.playerAsset.Assets;

public abstract class CombatAsset extends PlayerAsset {

    private int range;
    protected int offDamage;
    protected int defDamage;
    protected int armor;
    protected int maxHealth;
    protected int currentHealth;
    protected int upkeep;
    protected boolean poweredUp;
    protected String assetID;

    //Various getter and setters for attributes
    public void setRange(int range){
        this.range = range;
    }

    public void setID(String id){
        assetID = id;
    }
    public void depleteHealth(int n){
        if (armor >= n){
            armor -= n;
        }
        else{
            currentHealth -= (n - armor);
        }
    }
    public String getID(){
        return assetID;
    }
    public int getOffDamage(int distance){
        if (distance <= range){
            return offDamage;
        }
        else{
            return 0;
        }
    }
    public int getDefDamage(int distance){
        if (distance <= range){
            return defDamage;
        }
        else{
            return 0;
        }
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
