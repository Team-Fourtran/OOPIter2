package models.tileInfo;
/*
 * See AoE. This provides a negative infinity amount of health to apply to assets (depletes their health completely)
 */
public class InstantDeathAoE implements AoE {
	double deathEffect = Double.NEGATIVE_INFINITY;

	@Override
	// Returns double representing a double to add to a player assets' existing health
	public double getAreaEffect() {
		return deathEffect;
	}
	
	
}
