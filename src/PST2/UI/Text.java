package PST2.UI;

import PST2.StratEdge;
import processing.core.PFont;

public class Text extends GraphicObject
{
    private static final String IMGPATH = "res/font/";
    private final String text;
    private final PFont font;
    
    public Text(StratEdge se, int x, int y, int w, int h, int size, String text, String font)
    {
        super(se, x, y, w, h);
        this.text = text;
        this.font = se.createFont(IMGPATH + font, size);
    }

    @Override
    public void init(){}

    @Override
    public void draw()
    {
        se.textFont(font);
        se.fill(0);
        text(text, w/2, h/2);
    }

    @Override
    public void mousePressed(int x, int y){}

    @Override
    public void mouseMoved(int x, int y){}
    
    public String getText(){return text;}
    
}
