package models.visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import models.playerAsset.Assets.RallyPoint;
import models.playerAsset.Assets.Structures.*;
import models.playerAsset.Assets.Units.*;
import models.playerAsset.Assets.Army;
import models.tileInfo.*;

public class TileDrawingVisitor implements TileVisitor, SpecificAssetVisitor {
	private final int DRAWABLE_OBJECTS = 6; //Number of objects listed below
	private final int TERRAIN = 0;
	private final int UNIT = 1;
	private final int ARMY = 2;
	private final int STRUCTURE = 3;
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
		
		for (int i = 0; i < DRAWABLE_OBJECTS; i++) {
			ArrayList<BufferedImage> a = new ArrayList<>();
			priority.add(a);
		}
	}

	public Graphics2D getGraphic() {
		return g2;
	}

	@Override
	public void visitLandMine(LandMine item){
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/misc/Target.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/misc/Target.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
		texture = resizeImage(texture);
		addToPriorityQueue(ITEM, texture);
	}

	@Override
	public void visitObstacleItem(ObstacleItem item){
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/misc/Cursor.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/misc/Cursor.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
		texture = resizeImage(texture);
		addToPriorityQueue(ITEM, texture);
	}
	
	@Override
	public void visitNormal(Normal normal) {
	    BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Grass.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/terrain/Grass.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
        texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitSlowing(Slowing slowing) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Dirt.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/terrain/Dirt.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
		texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitImpassable(Impassable tile) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/terrain/Water.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/terrain/Water.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
		texture = resizeImage(texture);
		addToPriorityQueue(TERRAIN, texture);
	}

	@Override
	public void visitArmy(Army army) {
		BufferedImage texture = null;
		if (!army.hasBattleGroup()){
		    return;
        }
		try {
			texture = ImageIO.read(new File("src/application/images/units/BlueArmy.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/units/BlueArmy.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
        texture = resizeImage(texture);
		addToPriorityQueue(ARMY, texture);
	}


	@Override
	public void visitRallyPoint(RallyPoint rallyPoint) {
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("src/application/images/assets/Flag.png"));
		} catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/assets/Flag.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }		}
        texture = resizeImage(texture);
		addToPriorityQueue(RALLYPOINT, texture);
	}

    @Override
    public void visitCapital(Capital capital) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/application/images/assets/Building.png"));
        } catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/assets/Building.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitFarm(Farm farm) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/application/images/assets/Tower.png"));
        } catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/assets/Tower.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitFort(Fort fort) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/application/images/assets/Tower.png"));
        } catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/assets/Tower.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitMine(Mine mine) {

    }

    @Override
    public void visitObservationTower(ObservationTower observationTower) {

    }

    @Override
    public void visitPowerPlant(PowerPlant powerPlant) {

    }

    @Override
    public void visitUniversity(University university) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/application/images/assets/university.png"));
        } catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/assets/university.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitColonist(Colonist colonist) {
        BufferedImage texture = null;
        try{
            texture = ImageIO.read(new File("src/application/images/units/colonist.png"));
        } catch (IOException e) {
            try{
                texture = ImageIO.read(new File("../src/application/images/units/colonist.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
//        texture = resizeImage(texture);
        addToPriorityQueue(UNIT, texture);
    }

    @Override
    public void visitExplorer(Explorer explorer) {
        BufferedImage texture = null;
        try{
            texture = ImageIO.read(new File("src/application/images/units/red_explorer_trans.png"));
        } catch (IOException e) {
            try{
                texture = ImageIO.read(new File("../src/application/images/units/red_explorer_trans.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        //texture = resizeImage(texture);
        addToPriorityQueue(UNIT, texture);
    }

    @Override
    public void visitMeleeUnit(MeleeUnit meleeUnit) {
        BufferedImage texture = null;
        try{
            texture = ImageIO.read(new File("src/application/images/units/BlueSword.png"));
        } catch (IOException e) {
            try{
                texture = ImageIO.read(new File("../src/application/images/units/BlueSword.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(UNIT, texture);
    }

    @Override
    public void visitRangedUnit(RangedUnit rangedUnit) {
        BufferedImage texture = null;
        try{
            texture = ImageIO.read(new File("src/application/images/units/ranged.png"));
        } catch (IOException e) {
            try{
                texture = ImageIO.read(new File("../src/application/images/units/ranged.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitStructure(Structure structure) {
        BufferedImage texture = null;
        try {
            texture = ImageIO.read(new File("src/application/images/misc/Mountain.png"));
        } catch (IOException e) {
            try {
                texture = ImageIO.read(new File("../src/application/images/misc/Mountain.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(STRUCTURE, texture);
    }

    @Override
    public void visitUnit(Unit unit) {
        BufferedImage texture = null;
        try{
            texture = ImageIO.read(new File("src/application/images/misc/Mountain.png"));
        } catch (IOException e) {
            try{
                texture = ImageIO.read(new File("../src/application/images/misc/Mountain.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }        }
        texture = resizeImage(texture);
        addToPriorityQueue(UNIT, texture);
    }

    @Override
    public void visitResourcePackage(ResourcePackage resourcePackage) {
        String text = "E: " + Integer.toString(resourcePackage.getEnergyCount()) + "\n" +
                "O: " + Integer.toString(resourcePackage.getOreCount()) + "\n" +
                "F: " + Integer.toString(resourcePackage.getFoodCount());
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
//        try {
//            ImageIO.write(img, "png", new File("Text.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        img = resizeImage(img);
        int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
        BufferedImage resizedImage = new BufferedImage(64, 64, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(img, 0, 0, 64, 32, null);
        g.dispose();
        addToPriorityQueue(UNIT, resizedImage);
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
