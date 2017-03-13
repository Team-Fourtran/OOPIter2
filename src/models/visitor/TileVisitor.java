package models.visitor;

import models.tileInfo.*;

public interface TileVisitor extends ObjectVisitor {
	void visitNormal(Normal tile);
	void visitSlowing(Slowing tile);
	void visitImpassable(Impassable tile);
	void visitLandMine(LandMine item);
	void visitObstacleItem(ObstacleItem item);
}
