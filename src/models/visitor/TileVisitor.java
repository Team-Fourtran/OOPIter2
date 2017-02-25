package models.visitor;

import models.playerAssetNew.Structure;
import models.tileInfo.*;

public interface TileVisitor extends AssetVisitor {
	void visitNormal(Normal normal);
}
