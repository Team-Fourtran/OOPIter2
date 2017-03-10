package models.playerAsset.Assets.Units;

//Unit type
//Responsibilities: Weak attacker, uncovers resources on map
public class Explorer extends Unit{

    public Explorer(){
        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setRadiusOfInfluence(2);
        setMovesPerTurn(.33);
    }

    public String getType(){
        return "Explorer";
    }
}
