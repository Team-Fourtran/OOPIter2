package models.visitor;

import models.playerAssetNew.Structure;
import models.tileInfo.*;

public interface TileVisitor {
	void visitNormal(Normal normal);
	void visitStructure(Structure structure);
}
