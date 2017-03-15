package models.assetOwnership;

import java.util.HashMap;

import javafx.beans.Observable;
import models.playerAsset.Assets.Player;
import java.util.ArrayList;

/**
 *  keeps track of SEEN before
 * must go through player assets to tell if it can be updated live
 */
public class PlayerVisibility {
    private static PlayerVisibility myself;

    // everything the player has ever seen before
    private static HashMap<Player, ArrayList<TileAssociation>> seen = new HashMap<Player, ArrayList<TileAssociation>>();
    private static HashMap<Player, ArrayList<RadiusOfVisibilityAssociation>> rovList = new HashMap<Player, ArrayList<RadiusOfVisibilityAssociation>>();

    private PlayerVisibility() {

    }

    public static PlayerVisibility getInstance() {
        checkInstance();
        return myself;
    }

    private static void checkInstance() {
        if (myself == null) {
            myself = new PlayerVisibility();
        }
    }

    // whenever the view receives an update, it only can retrieve from the list below:
    // the rest needs to be shrouded
    public static ArrayList<TileAssociation> getTilesForPlayer(Player p) {
        ArrayList<TileAssociation> tiles = new ArrayList<TileAssociation>();
        for (RadiusOfVisibilityAssociation r : rovList.get(p)) {
            tiles.addAll(r.getRadiusOfVisibility());
        }
        for (TileAssociation t : seen.get(p)) {
            tiles.add(t);
            seen.remove(t);
        }
        return tiles;
    }

    /*
     * sets all tiles the Player p has seen
     * these shall be updated, but only when a radius changes (asset moves around, is added)
     */
    public static void setSeenTiles(RadiusOfVisibilityAssociation rov, Player p) {
        if (seen.isEmpty() || !seen.containsKey(p)) {
            seen.put(p, new ArrayList<TileAssociation>());
        }
        if (rovList.isEmpty() || !rovList.containsKey(p)) {
            rovList.put(p, new ArrayList<RadiusOfVisibilityAssociation>());
        }
        rovList.get(p).add(rov);
        for (int i = 0; i < rov.getTilesWithinRadius().size(); i++) {
            TileAssociation t = rov.getTilesWithinRadius().get(i);
            if (!seen.get(p).contains(t)) {
                seen.get(p).add(t);
            }
        }
    }
}
