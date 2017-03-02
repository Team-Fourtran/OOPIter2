package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class MeleeUnit extends Unit{

    public MeleeUnit(){
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setMovesPerTurn(.33);
    }

    public String getType(){
        return "Melee";
    }
}