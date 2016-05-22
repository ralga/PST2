package PST2.UI;

import PST2.StratEdge;
import processing.core.PFont;

public class Debug extends GraphicObject
{
    private final boolean DEBUG = true;                                         //True : affiche des informations de debuggage et de performance
    PFont font;

    public Debug(StratEdge se, int x, int y, int w, int h)
    {
        super(se, x, y, w, h);
        font = se.createFont("res/font/FreeMono.ttf", 15);

    }

    @Override
    public void init(){}
    
    @Override
    public void draw()
    {
        if(DEBUG)                                                               //Si le mode debug est actif
        {
            se.g.fill(255);
            se.g.stroke(0);
            se.g.strokeWeight(2);
            rect(0, 0, w, h);
            se.g.fill(0);
            se.textFont(font);
            text((int) (se.getFPS()) + " FPS", w/2, h/2);
        }
    }

    @Override
    public void mousePressed(int x, int y){}

    @Override
    public void mouseMoved(int x, int y){}
}
