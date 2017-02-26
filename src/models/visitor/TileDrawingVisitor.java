package models.visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.playerAsset.PlayerAsset;
import models.playerAsset.RallyPoint;
import models.playerAsset.Unit;
import models.playerAsset.Army;
import models.tileInfo.Normal;

public class TileDrawingVisitor implements TileVisitor {
	private Graphics2D g2;
	private int x;
	private int y;
	
	public TileDrawingVisitor(int x, int y, Graphics2D g2) {
		this.g2 = g2;
		this.x = x;
		this.y = y;
		// TODO Why? Reset image in graphic
		g2.drawImage(null, x, y, null);
	}

	public Graphics2D getGraphic() {
		return g2;
	}
	
	@Override
	public void visitNormal(Normal normal) {
	    BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

			g2.drawImage(texture, x+19, y+19, null);
	}

	@Override
	public void visitUnit(Unit unit) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Snow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
	}

	@Override
	public void visitArmy(Army army) {
		BufferedImage texture = null;
		if (!army.hasBattleGroup()){
		    return;
        }
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Water.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);

	}

	@Override
	public void visitStructure(PlayerAsset structure) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/assets/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/assets/Flag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			g2.drawImage(texture, x+19, y+19, null);

	}

}
