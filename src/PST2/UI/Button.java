package PST2.UI;

import PST2.StratEdge;

public class Button extends GraphicObject
{   
    private static final String IMGPATH = "res/img/button/";
    
    private final int id;
    private Text t = null;
    private int img, img1, img2;
    private int opacity = 80;
    
    public Button(StratEdge se, Text t, int id)
    {
        super(se, t.getX() - (se.width-se.getW())/2, t.getY() - (se.height-se.getH())/2, t.getW(), t.getH());
        this.t = t;
        this.id = id;
    }
    
    public Button(StratEdge se, int x, int y, int img1, int img2, int id)
    {
        super(se, x, y, 0, 0, IMGPATH);
        this.img1 = this.img = img1;
        this.img2 = img2;
        this.w = tabImg[img1].width;
        this.h = tabImg[img1].height;
        this.id = id;
    }
    
    @Override
    public void init() {}

    @Override
    public void draw() 
    {
        if(t != null)
        {
            se.fill(255, opacity);
            rect(0, 0, w, h, 80);
        }
        else
            image(tabImg[img], 0, 0);
    }

    @Override
    public void mousePressed(int x, int y)
    {
        
        switch(id)
        {
            case 0:                                                             //Actions lors d'un clic de souris sur le bouton jouer
                se.setView(1);
                break;
            case 1:                                                             //Actions lors d'un clic de souris sur le bouton quitter
                StratEdge.getSE().exit();
                break;
            case 2:
                se.setView(0);
                break;
        }
    }

    @Override
    public void mouseMoved(int x, int y)
    {
        if(isOn(x, y))
        {
            opacity = 190;
            img = img2;
        }
        else
        {
            opacity = 80;
            img = img1;
        }
            
        if(se.getViews()[0].getGO()[2].isOn(x, y) || se.getViews()[0].getGO()[4].isOn(x, y))
            se.getSurface().setCursor(12);                                      //Modification de l'apparence du curseur de la souris lorsque l'on est au-dessus d'un bouton
        else
            se.getSurface().setCursor(0);
    }
}