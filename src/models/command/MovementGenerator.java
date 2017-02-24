//package models.command;
//
//
//import models.assetOwnership.GameMap;
//import models.assetOwnership.TileAssociation;
//import models.playerAssetNew.PlayerAsset;
//
//import java.util.ArrayList;
//
//public class MovementGenerator {
//
//    public MovementGenerator(PlayerAsset asset,  TileAssociation end){
//        ArrayList<TileAssociation> path = GameMap.generatePath(asset, end);
//        TileAssociation cur = start;
//        for (TileAssociation next : path){
//            asset.addCommand(
//                    new MoveCommand(asset, cur, next)
//            );
//            cur = next;
//        }
//    }
//}
