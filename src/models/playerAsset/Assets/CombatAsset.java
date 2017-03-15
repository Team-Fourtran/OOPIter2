package models.playerAsset.Assets;

public abstract class CombatAsset extends PlayerAsset {

    private int offDamage;
    private int defDamage;
    private int armor;
    private int maxHealth;
    private int currentHealth;
    private int upkeep;
    private int radiusOfInfluence;
    private boolean poweredUp;
    private String assetID;
    public int visibility;

    //Various getter and setters for attributes

    public void setMaxHealth(int maxHealth){this.maxHealth = maxHealth;}

    public void setCurrentHealth(int currentHealth){this.currentHealth = currentHealth;}

    public void setArmor(int armor){this.armor = armor;}

    public void setOffDamage(int offDamage){
        this.offDamage = offDamage;
    }

    public void setDefDamage(int defDamage){this.defDamage = defDamage;}

    public void setUpkeep(int upkeep){this.upkeep = upkeep;}
    
    public void setRadiusOfInfluence(int radius){this.radiusOfInfluence = radius;}

    public void setID(String id){
        assetID = id;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void depleteHealth(int n){
    	
        if (armor > 0){
            if (armor >= n){
                armor -= n;
            }
            else{
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
        if (distance <= radiusOfInfluence){
        	// return percentage of offDamage based off difference between radius of influence and distance
            return offDamage;
        }
        else{
            return 0;
        }
    }

    public int getDefDamage(int distance){
        if (distance <= radiusOfInfluence){
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
    
    public int getRadiusOfInfluence() {
    	return radiusOfInfluence;
    }

    public int getVisibility(){
        return visibility;
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
