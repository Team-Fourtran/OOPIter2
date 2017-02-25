package models.visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.assetOwnership.TileAssociation;
import models.playerAssetNew.Structure;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;
import views.MainScreen;

public class TileDrawingVisitor implements TileVisitor {
	private Graphics2D g2;
	private int x;
	private int y;
	private TileAssociation tile;
	
	public TileDrawingVisitor(int x, int y, Graphics2D g2, TileAssociation tile) {
		this.g2 = g2;
		this.x = x;
		this.y = y;
		this.tile = tile;
	}

	public Graphics2D getGraphic() {
		return g2;
	}
	
	@Override
	public void visitNormal(Normal normal) {
	    BufferedImage texture = null;
		try {
				texture = ImageIO.read(new File("src/views/Grass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2.drawImage(texture, x, y, null);
	}

	@Override
	public void visitStructure(Structure structure) {
		System.out.println("Struct");
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/views/Building.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			g2.drawImage(texture, x, y, null);
	}

}
