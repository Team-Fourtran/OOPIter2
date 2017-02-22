package models.playerAsset;

//Unit type
//Responsibilities: Attacker, can attack over multiple tiles
public class RangedUnit extends Unit{

    private final int range; //number of tiles ranger can shoot across?

    public RangedUnit(){
        offDamage = 75;
        defDamage = 25;
        armor = 25;
        movesPerTurn = 2;
        maxHealth = currentHealth = 150;
        upkeep = 20;
        range = 2;
    }

    public String getType(){
        return "Ranged";
    }

}