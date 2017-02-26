package models.tileInfo;

/*
 * See AoE. This provides a negative amount of health to apply to assets
 */
public class DamageAoE implements AoE {
	private double damageEffect = -20;

	@Override
	// Returns double representing a double to add to a player assets' existing health
	public double getAreaEffect() {
		return damageEffect;
	}

}
