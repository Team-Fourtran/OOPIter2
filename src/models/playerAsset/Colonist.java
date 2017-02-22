package application.models.playerAsset;

//Unit type
//Responsibilities: Weak attacker, can be consumed to make structure
public class Colonist extends Unit{

    public Colonist(){
        offDamage = 0;
        defDamage = 0;
        armor = 0;
        movesPerTurn = 2;
        maxHealth = currentHealth = 100;
        upkeep = 8;
    }

    public String getType(){
        return "Colonist";
    }
}