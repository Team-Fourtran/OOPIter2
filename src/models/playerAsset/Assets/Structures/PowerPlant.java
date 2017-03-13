package models.playerAsset.Assets.Structures;

import models.playerAsset.Assets.Worker;

import java.util.ArrayList;

public class PowerPlant extends Structure {

    ArrayList<Worker> gatherers;
    ArrayList<Worker> producers;

    public PowerPlant() {

        setOffDamage(75);
        setDefDamage(75);
        setArmor(150);
        setMaxHealth(200);
        setCurrentHealth(200);
        setUpkeep(1);
        setProductionRate(1);
        staff = new ArrayList<>();
        gatherers = new ArrayList<>();
        producers = new ArrayList<>();

    }

}
