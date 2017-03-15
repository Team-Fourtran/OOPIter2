package models.playerAsset.Assets;

import models.assetOwnership.Radius;
import models.assetOwnership.TileAssociation;
import models.assetOwnership.WorkRadius;
import models.playerAsset.Assets.Structures.*;

public class StructureFactory {

    //factory method to make any of the four units
    public Structure makeStructure(String type, TileAssociation baseTile){
        switch (type){
            case "capital":
            	Capital c = new Capital();
            	configureWorkRadius(c, baseTile);
            	// player needs to configure capital resource type
                return c;
            case "farm":
            	Farm f = new Farm();
            	WorkRadius rf = configureWorkRadius(f, baseTile);
            	f.setHarvestType(new FoodHarvestStrategy(rf, f));
            	return f;
            case "mine":
            	Mine m = new Mine();
            	WorkRadius rm = configureWorkRadius(m, baseTile);
            	m.setHarvestType(new OreHarvestStrategy(rm, m));
            	return m;
            case "power plant":
            	PowerPlant p = new PowerPlant();
            	WorkRadius rp = configureWorkRadius(p, baseTile);
            	p.setHarvestType(new EnergyHarvestStrategy(rp, p));
            	return p;
            case "fort":
                return new Fort();
            case "observation tower":
                return new ObservationTower();
            case "university":
                return new University();
        }

        return null;
    }
    
    private WorkRadius configureWorkRadius(ResourceStructure s, TileAssociation baseTile) {
    	WorkRadius workRadius = new WorkRadius(baseTile, s.getWorkRadiusSize());
    	s.setWorkRadius(workRadius);
    	return workRadius;
    }
}

