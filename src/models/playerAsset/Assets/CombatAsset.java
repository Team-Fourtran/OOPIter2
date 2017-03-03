package models.playerAsset.Assets;

public abstract class CombatAsset extends PlayerAsset {

    private int range;
<<<<<<< HEAD
    private int offDamage;
    private int defDamage;
    private int armor;
    private int maxHealth;
    private int currentHealth;
    private int upkeep;
    private boolean poweredUp;
    private String assetID;
=======
    protected int offDamage;
    protected int defDamage;
    protected int armor;
    protected int maxHealth;
    protected int currentHealth;
    protected int upkeep;
    protected boolean poweredUp;
>>>>>>> CommandGeneration

    //Various getter and setters for attributes

    public void setRange(int range){
        this.range = range;
    }

    public void setMaxHealth(int maxHealth){this.maxHealth = maxHealth;}

    public void setCurrentHealth(int currentHealth){this.currentHealth = currentHealth;}

    public void setArmor(int armor){this.armor = armor;}

    public void setOffDamage(int offDamage){
        this.offDamage = offDamage;
    }

    public void setDefDamage(int defDamage){this.defDamage = defDamage;}

    public void setUpkeep(int upkeep){this.upkeep = upkeep;}

    public void setID(String id){
        assetID = id;
    }

    public void depleteHealth(int n){
        if (armor >= 0){
            if (armor >= n){
                armor -= n;
            }
            else{
                currentHealth -= (n-armor);
                armor = 0;
            }
        }
        else{
            currentHealth -= (n - armor);
        }
    }

    public void incrementHealth(int n){
        currentHealth += n;
        if (currentHealth > maxHealth){
            currentHealth = maxHealth;
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
        if (!poweredUp){
            poweredUp = true;
            upkeep *= 4;
        }
    }

    //Power down a powered up unit and change its resource consumption to %25
    public void powerDown(){
        if (poweredUp){
            poweredUp = false;
            upkeep /= 4;
        }
    }
}
