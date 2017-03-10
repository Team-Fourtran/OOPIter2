package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Attacker, can attack over multiple tiles
public class RangedUnit extends Unit{

    public RangedUnit(){
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(2);
        setMovesPerTurn(.33);
        setRange(3);
    }
    public String getType(){
        return "Ranged";
    }
}