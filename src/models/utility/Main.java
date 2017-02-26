package models.utility;


import models.assetOwnership.GameMap;
import models.assetOwnership.TileAssociation;
import models.command.MoveRallyPointCommand;
import models.playerAssetNew.*;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;
import views.MainScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
	private static MainScreen mainScreen;
	
    public static void main(String[] args) throws InterruptedException {
        TileGen tileGen = new TileGen(30, 30);
        tileGen.execute();
        TileAssociation[] _tiles = tileGen.returnTileAssoc();

        Unit u1 = new Colonist();
        _tiles[4].add(u1);
        Unit u2 = new Explorer();
        _tiles[9].add(u2);
        Unit u3 = new Explorer();
        
        Army a1 = new Army(u3, u1, u2);
        _tiles[14].add(a1);
        
        RallyPoint r = new RallyPoint();
        r.setArmy(a1);
        _tiles[14].add(r);
        
        mainScreen = new MainScreen(_tiles);
        mainScreen.initialize();
        mainScreen.generateMainScreen();
        mainScreen.showMainScreen();
      
        ArrayList<TileAssociation> arrayList = new ArrayList<TileAssociation>(Arrays.asList(_tiles));
        GameMap map = new GameMap(arrayList, 30, 30);
//        map.debugPrint();
        Thread.sleep(3000);
        MoveRallyPointCommand mrp = new MoveRallyPointCommand(
                r,
                _tiles[22],
                map
        );
        mrp.execute();
//
//        map.debugPrint();
//        Thread.sleep(1000);
    }
}
