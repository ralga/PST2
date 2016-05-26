package PST2;

import PST2.Capacity.Capacity;
import PST2.Piece.SEPiece;
import PST2.IO.Read;
import PST2.Piece.*;
import PST2.UI.*;
import processing.core.*; 
import processing.event.MouseEvent;

public class StratEdge extends PApplet
{
    /*Constantes*/
    private final String TITLE = "Strat::Edge";                                 //Nom fen
    private final int FPS = 60;                                                 //Images par seconde max
    
    /*Variables*/
    private int view = 0;
    private View[] tabView;
    private Team t1, t2;
    private int w;                                                              //Largeur fenêtre
    private int h;                                                              //Hauteur fenêtre
    private static StratEdge se;                                                //Instance courante de StratEdge

    public static void main(String[] args)
    {
        PApplet.main(new String[]{PST2.StratEdge.class.getName()});
    }
    
    public void initViews()
    {
        tabView = new View[2];
        tabView[0] = new Menu();
        tabView[1] = new Game();
    }
    
    @Override
    public void settings()
    {
        se = this;                                                              //On récupère l'instance de StratEdge qui vient d'être créée
        w = 1280;
        h = 720;
//        fullScreen(P2D);
        size(w, h, P2D);                                                        //Taille de la fenêtre
    }
    
    @Override
    public void setup()
    {
        SEPiece.load(new Read());                                               //Charge tout ce qui concerne les pièces
        
        frameRate(FPS);
        surface.setTitle(TITLE);                                                //Modifie le titre de la fen
        
        background(0);                                                          //Couleur d'arrière plan dans la fenêtre
        stroke (0);
        
        initViews();
        t1 = new Team(new int[]{10, 9, 8, 7, 6, 8, 9, 10, 11, 11, 11, 11, 11, 11, 11, 11}, Piece.TEAM1);
        t2 = new Team(new int[]{4, 3, 2, 1, 0, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5}, Piece.TEAM2);
        getGame().init(t1, t2);
        for(Capacity p : Capacity.getPassive(true))
            p.power();
        for(Capacity p : Capacity.getPassive(false))
            p.power();
    }
    
    @Override
    public void draw() 
    {
        for(GraphicObject go : tabView[view].getGO())
            go.draw();
        for(Button go : tabView[view].getButts())
            go.draw();
    }
    
    
    @Override
    public void mousePressed(MouseEvent event)
    {
        for(GraphicObject go : tabView[view].getGO())
            if(go.isOn(event.getX(), event.getY()))
                go.mousePressed(event.getX(), event.getY());
        for(Button go : tabView[view].getButts())
            if(go.isOn(event.getX(), event.getY()))
                go.mousePressed(event.getX(), event.getY());
    }

    @Override
    public void mouseMoved(MouseEvent event)
    {
        se.getSurface().setCursor(0);
        for(GraphicObject go : tabView[view].getGO())
            go.mouseMoved(event.getX(), event.getY());
        for(Button go : tabView[view].getButts())
            go.mouseMoved(event.getX(), event.getY());
    }
    
    /*Getters*/
    public static StratEdge getSE(){return se;}
    public int getW(){return w;}
    public int getH(){return h;}
    public double getFPS(){return frameRate;}
    public Game getGame(){return (Game)tabView[1];}
    public View[] getViews(){return tabView;}
    
    /*Setters*/
    public void setView(int v){view = v;}                                       //Permet de modifier la "vue"
}