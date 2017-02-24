package src.models.playerAsset;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class MeleeUnit extends Unit{

    public MeleeUnit(){
        offDamage = 75;
        defDamage = 50;
        armor = 50;
        movesPerTurn = 2;
        maxHealth = currentHealth = 150;
        upkeep = 20;
    }

    public String getType(){
        return "Melee";
    }
}