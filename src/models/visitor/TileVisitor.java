package models.visitor;

import models.tileInfo.*;

public interface TileVisitor extends AssetVisitor {
	void visitNormal(Normal tile);
	void visitSlowing(Slowing tile);
	void visitImpassable(Impassable tile);
	void visitOneShotItem(OneShotItem item);
	void visitObstacleItem(ObstacleItem item);
}
