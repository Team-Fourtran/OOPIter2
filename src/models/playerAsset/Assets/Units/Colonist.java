package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Weak attacker, can be consumed to make structure
public class Colonist extends Unit{

    public Colonist(){
        offDamage = 0;
        defDamage = 0;
        armor = 0;
        setMovesPerTurn(.33);
        maxHealth = currentHealth = 100;
        upkeep = 8;
    }

    public String getType(){
        return "Colonist";
    }
}
