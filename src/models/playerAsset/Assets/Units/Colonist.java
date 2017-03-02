package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Weak attacker, can be consumed to make structure
public class Colonist extends Unit{

    public Colonist(){
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setMovesPerTurn(.33);

    }

    public String getType(){
        return "Colonist";
    }
}
