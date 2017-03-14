package models.playerAsset.Assets.Structures;

import java.util.ArrayList;
import models.playerAsset.Assets.Technology.*;
import models.visitor.PlayerVisitor;

public class University extends Structure{

    TechFactory factory;
    public University() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
        staff = new ArrayList<>();
        factory = new TechFactory();
    }

    public Technology discoverTechnology(String tech){
        return factory.makeTech(tech);
    }
}
