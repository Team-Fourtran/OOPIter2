package models.playerAsset.Assets.Technology;

/**
 * Created by Clay on 3/13/2017.
 */
public class TechFactory {

    public Technology makeTech(String type){
        switch(type){
            case "attack": return new OffenseDamageTech();
            case "defense": return new DefenseDamageTech();
            case "armor": return new ArmorTech();
            case "health": return new MaxHealthTech();
            case "movement": return new MovementTech();
            case "efficiency": return new EfficiencyTech();
            case "visibility": return new VisibilityTech();
            case "density": return new DensityTech();
            case "radius": return new WorkRadiusTech();
            case "food": return new FoodProductionTech();
            case "ore": return new OreProductionTech();
            case "energy": return new EnergyProductionTech();
        }
        return null;
    }
}
