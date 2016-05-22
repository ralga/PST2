package PST2.UI;

import PST2.StratEdge;

public class Pictures extends GraphicObject{
    private static final String IMGPATH = "res/img/powers/";
    private final int img;
    
    public Pictures(StratEdge se, int img)
    {
        super(se, 0, 0, se.getW(), se.getH(), IMGPATH);
        this.img = img;
        
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
