package PST2.UI;

import PST2.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Description extends GraphicObject 
{

    private static final String path = "res/img/des/";
    private static final String fontP = "res/font/";
    private final Game g;
    private final PFont font;
    private final int size;
    
    public Description(StratEdge se, int x, int y, int w, int h,String font,int size) 
    {
        super(se, x, y, w, h,path);
        this.g = se.getGame();
        this.font=se.createFont(fontP + font, size);
        this.size=size;
        
    }

    @Override
    public void init(){}

    @Override
    public void draw()
    {
        image(tabImg[12], 0, 0);                                                //Affiche le background
        if(g.getSelection()!=null)
        {                                             
            PImage i = tabImg[g.getSelection().getImg()];                       //Récupère l'image associée à la pièce selectionnée
            image(i,20,30);                                                     //Affiche l'image
            se.textFont(font);                                                  //Déclare la font et la taille de police à utiliser
            /*On ne fait pas appel à la méthode text de GraphicObject car
            * l'allignement se fait par la gauche et pas le centre comme 
            * dans celle-ci
            */
            se.textAlign(PApplet.LEFT);                                    
            se.g.text(g.getSelection().getName(), x + i.width+30, y +size+10);  //Affiche le nom 10 px en dessous du haut du cadre
            se.g.text("Vie :" + g.getSelection().getLife(), 
                    x + i.width+30, y + 2*(size+2)+10);                         //Affiche la vie
            se.g.text("Attaque :" + g.getSelection().getAtt(), 
                    x + i.width+30, y + 3*(size+2)+10);                         //Affiche l'attaque
            se.g.text("Défense :" + g.getSelection().getDef(), 
                    x + i.width+30, y + 4*(size+2)+10);                         //Affiche la Defense
            se.g.text("Cooldown c1 :" + g.getSelection().getCapacity1().getCurrentCool(), x+i.width+30, y + 5*(size+2)+10);
            se.g.text("Cooldown c2 :"+ g.getSelection().getCapacity2().getCurrentCool(), x+i.width+30, y + 6*(size+2)+10);
        }
    }

    @Override
    public void mousePressed(int x, int y)
    {
    }

    @Override
    public void mouseMoved(int x, int y) 
    {
    }
}