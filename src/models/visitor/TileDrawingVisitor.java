package models.visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.assetOwnership.TileAssociation;
import models.playerAssetNew.Army;
import models.playerAssetNew.PlayerAsset;
import models.playerAssetNew.RallyPoint;
import models.playerAssetNew.Structure;
import models.playerAssetNew.Unit;
import models.tileInfo.Normal;
import models.tileInfo.Terrain;
import models.tileInfo.Tile;
import views.MainScreen;
import views.hexMech;

public class TileDrawingVisitor implements TileVisitor {
	private Graphics2D g2;
	private int x;
	private int y;
	
	public TileDrawingVisitor(int x, int y, Graphics2D g2) {
		this.g2 = g2;
		this.x = x;
		this.y = y;
		// Reset image in graphic
		g2.drawImage(null, x, y, null);
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
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
	}

	@Override
	public void visitUnit(Unit unit) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/views/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x, y, null);
	}

	@Override
	public void visitArmy(Army army) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/views/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
		
	}

	@Override
	public void visitStructure(PlayerAsset structure) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/views/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/views/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
		
	}

}
