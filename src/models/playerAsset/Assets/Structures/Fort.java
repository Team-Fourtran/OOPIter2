package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Units.Unit;

import java.util.ArrayList;

public class Fort extends Structure{

    private ArrayList<Unit> trainers;

    public Fort() {

        offDamage = 75;
        defDamage = 75;
        armor = 150;
        maxHealth = currentHealth = 200;
        upkeep = 1;
        productionRate = 0;
        staff = new ArrayList<>();
        trainers = new ArrayList<>();

    }
}
