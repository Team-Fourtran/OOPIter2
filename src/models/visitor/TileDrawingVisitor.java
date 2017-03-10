package models.visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.Structure;
import models.playerAsset.Assets.Units.Unit;
import models.playerAsset.Assets.Army;
import models.tileInfo.*;

public class TileDrawingVisitor implements TileVisitor {
	private final int TERRAIN = 0;
	private final int STRUCTURE = 1;
	private final int UNIT = 2;
	private final int ARMY = 3;
	private final int ITEM = 4;
	private final int RALLYPOINT = 5;
	
	private Graphics2D g2;
	private int x;
	private int y;
	private ArrayList<ArrayList<BufferedImage>> priority = new ArrayList<>();
	
	public TileDrawingVisitor(int x, int y, Graphics2D g2) {
		this.g2 = g2;
		this.x = x;
		this.y = y;
		// TODO Why? Reset image in graphic
		g2.drawImage(null, x, y, null);
		
		for (int i = 0; i < 5; i++) {
			ArrayList<BufferedImage> a = new ArrayList<>();
			priority.add(a);
		}
	}

	public Graphics2D getGraphic() {
		return g2;
	}

	@Override
	public void visitOneShotItem(OneShotItem item){
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/misc/Money.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		texture = resizeImage(texture);
		addToPriorityQueue(ITEM, texture);
	}

	@Override
	public void visitObstacleItem(ObstacleItem item){
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/misc/Cursor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		texture = resizeImage(texture);
		addToPriorityQueue(ITEM, texture);
	}
	
	@Override
	public void visitNormal(Normal normal) {
	    BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitSlowing(Slowing slowing) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitImpassable(Impassable tile) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Water.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitUnit(Unit unit) {
		BufferedImage texture = null;
		try{
			texture = ImageIO.read(new File("src/application/images/terrain/Snow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        texture = resizeImage(texture);
		addToPriorityQueue(UNIT, texture);
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
        texture = resizeImage(texture);
		addToPriorityQueue(ARMY, texture);
	}

	@Override
	public void visitStructure(Structure structure) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/assets/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        texture = resizeImage(texture);
		addToPriorityQueue(STRUCTURE, texture);
	}

	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/assets/Flag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        texture = resizeImage(texture);
		addToPriorityQueue(RALLYPOINT, texture);
	}

    private BufferedImage resizeImage(BufferedImage texture){
        int type = texture.getType() == 0? BufferedImage.TYPE_INT_ARGB : texture.getType();
        BufferedImage resizedImage = new BufferedImage(64, 64, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(texture, 0, 0, 64, 64, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
        //g2.drawImage(resizedImage, x+14, y+10, null);
    }
	
	// takes in the index (priority) and also the texture
	public void addToPriorityQueue(int PRIORITY, BufferedImage bt) {
		priority.get(PRIORITY).add(bt);
	}
	
	public void drawTile() {
		for (int i = 0; i < priority.size(); i++) {
			ArrayList<BufferedImage> a = priority.get(i);
			if (i == 0) {
			}
			for (int j = 0; j < a.size(); j++) {
				g2.drawImage(a.get(j), x+14, y+10, null);
			}
		}
	}

}