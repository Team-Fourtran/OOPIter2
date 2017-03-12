package models.tileInfo;


import models.visitor.TileVisitor;

public class LandMine extends OneShotItem {

    @Override
    public void accept(TileVisitor v) {
        v.visitLandMine(this);
    }
}
