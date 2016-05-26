package PST2.UI;

import PST2.*;
import java.io.File;
import processing.core.*;

public abstract class GraphicObject
{   
    private static final String[] ACCEPTED_EXT = {".png", ".jpg", ".gif",".jpeg"};      //Extensions d'image autorisées
    protected final StratEdge se;
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected int r;
    protected PImage[] tabImg;
    
    protected GraphicObject(StratEdge se, int x, int y, int w, int h)
    {
        this.se = se;
        this.x = (se.width-se.getW()) / 2 + x;
        this.y = (se.height-se.getH()) / 2 + y;
        this.w = w;
        this.h = h;
    }
    
    protected GraphicObject(StratEdge se, int x, int y, int w, int h, String path)
    {
        this(se, x, y, w, h);
        loadImages(path);
    }
    
    protected GraphicObject(StratEdge se, int x, int y, int r, String path)     //Objet graphique de forme circulaire
    {
        this(se, x, y, -1, -1, path);
        this.r = r;
    }
    
    private void loadImages(String path)                                        //Charge un dossier d'images dans un tableau de PImage
    {
        String na;
        File[] files = new File(path).listFiles();                              //files contient tous les fichiers de path
        tabImg = new PImage[files.length];
        for(int i = 0, ind; i < files.length; i++)                              //Pour chacun des fichiers de path
        {
            na = files[i].getName();                                            //na contient le nom du fichier i
            String ext = na.substring(na.lastIndexOf("."));                     //Récupération de l'extension de fichier
            for(String a_ext : ACCEPTED_EXT)                                    
                if(ext.equals(a_ext))                                           //Si l'extension est accepté
                {   
                    ind = Integer.parseInt(na.substring(0,na.lastIndexOf(".")));//Récupération de l'index (nom du fichier sans l'extension)
                    tabImg[ind] = se.loadImage(path + na);                      //Chargement de l'image
                }
        }
    }

    protected void image(PImage i, int rx, int ry)
    {
        se.g.image(i, x + rx, y + ry);
    }
    
    protected void rect(int rx, int ry, int w, int h)
    {
        se.g.rect(x + rx, y + ry, w, h);
    }
    
    protected void rect(int rx, int ry, int w, int h, int r)
    {
        se.g.rect(x + rx, y + ry, w, h, r);
    }
    
    protected void ellipse(int rx, int ry, int w, int h)
    {
        se.g.ellipseMode(PApplet.CENTER);
        se.g.ellipse(x + rx, y + ry, w, h);
    }
    
    protected void text(String txt, int rx, int ry)
    {
        se.textAlign(PApplet.CENTER);
        se.g.text(txt, x + rx, y + ry);
    }
    
    public boolean isOn(int mx, int my)                                         //Renvoie true si le point (mx, my) appartient au GO
    {
        if(w != -1)
            return (mx >= x) && (mx < x+w) && (my >= y) && (my < y+h);
        else
        {
            int dx = Math.abs(x-mx);
            int dy = Math.abs(y-my);
            return Math.sqrt(dx*dx + dy*dy) <= r;
        }
    }
    
    /*Getters*/
    public int getX(){return x;}
    public int getY(){return y;}
    public int getW(){return w;}
    public int getH(){return h;}
    public int getRX(int px){return px - x;}                                    //Position relative en x
    public int getRY(int py){return py - y;}                                    //Position relative en y
    
    /*Setters*/
    public void setX(int nx){x = (se.width-se.getW()) / 2 + nx;}
    public void setY(int ny){y = (se.height-se.getH()) / 2 + ny;}
    
    /*Abstract methods*/
    public abstract void init();                                                //Gestion de l'initialisation du composant graphique
    public abstract void draw();                                                //Gestion de l'affichage du composant à l'aide de processing (60 ips)
    public abstract void mousePressed(int x, int y);                            //Gestion des clics de souris
    public abstract void mouseMoved(int x, int y);                              //Gestion du mouvement de la souris
}