package models.visitor;

import models.tileInfo.*;

public interface TileVisitor extends AssetVisitor {
	void visitNormal(Normal normal);
}
