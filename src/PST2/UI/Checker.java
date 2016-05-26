package PST2.UI;

import PST2.*;
import processing.core.PGraphics;

import static PST2.Game.C;
import processing.core.PApplet;

public class Checker extends GraphicObject
{
    private static final String IMGPATH = "res/img/checker/";
    public static final int W = 560;
    private PGraphics img;
    
    public Checker(StratEdge se, int x, int y)
    {
        super(se, x, y, W, W, IMGPATH);
    }

    @Override
    public void init()
    {
        img = se.createGraphics(W+2, W+2);
        img.beginDraw();
        for(int i = 0; i < C; i++)
            for(int j = 0; j < C; j++)
                img.image(tabImg[(i+j)%2], i * w/C+1, j * h/C+1);
        img.noFill();
        img.strokeWeight(1);
        img.rect(0, 0, W+1, W+1);
        img.endDraw();
    }
    
    @Override
    public void draw() 
    {
        se.g.imageMode(PApplet.CORNER);
        image(img, -1, -1);
    }
    
    @Override
    public void mousePressed(int x, int y) {}

    @Override
    public void mouseMoved(int x, int y) {}
}
