package PST2.UI;

import PST2.StratEdge;

public class Background extends GraphicObject
{
    private static final String IMGPATH = "res/img/background/";
    private final int img;
    
    public Background(StratEdge se, int img)
    {
        super(se, 0, 0, se.getW(), se.getH(), IMGPATH);
        this.img = img;
    }

    @Override
    public void init(){}
    
    @Override
    public void draw() 
    {
        image(tabImg[img], 0, 0);
    }
    
    @Override
    public void mousePressed(int x, int y) {}

    @Override
    public void mouseMoved(int x, int y) {}
}
