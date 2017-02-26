package models.playerAsset;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class MeleeUnit extends Unit{

    public MeleeUnit(){
        offDamage = 75;
        defDamage = 50;
        armor = 50;
        setMovesPerTurn(.33);
        maxHealth = currentHealth = 150;
        upkeep = 20;
    }

    public String getType(){
        return "Melee";
    }
}