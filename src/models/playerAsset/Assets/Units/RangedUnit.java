package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Attacker, can attack over multiple tiles
public class RangedUnit extends Unit{

    public RangedUnit(){
        offDamage = 75;
        defDamage = 25;
        armor = 25;
        setMovesPerTurn(.33);
        maxHealth = currentHealth = 150;
        upkeep = 20;
        setRange(3);
    }
    public String getType(){
        return "Ranged";
    }
}