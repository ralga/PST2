package PST2.UI;

import PST2.Piece.Piece;
import PST2.*;

import static PST2.Game.C;
import processing.core.PApplet;

public class Armies extends GraphicObject
{
    private static final String IMGPATH = "res/img/piece/";
    
    private final Game game;
    private boolean[][] pMoves;
    
    public Armies(StratEdge se, int x, int y) 
    {
        super(se, x - (se.width-se.getW())/2, y - (se.height-se.getH())/2, Checker.W, Checker.W, IMGPATH);
        game = se.getGame();
    }
    
    public void changeSelection(Piece nSelec)                                   //Modifie la sélection si le tour est correct
    {
        if(nSelec == null)
        {
            game.setSelection(null);
            return;
        }
        int t = nSelec.getTeam() ? 1 : 0;                                           
        if(game.getTurn() % 2 != t)                                             //Si c'est au tour de l'équipe de nSelec
        {
            game.setSelection(nSelec);                                          //On modifie la sélection
            pMoves = nSelec.getMoves(game.getChecker(), true);                  //On met à jour les mouvements
        }
    }
    
    @Override
    public void init(){}
    
    @Override
    public void draw()
    {
        se.g.imageMode(PApplet.CORNER);
        for(Piece[] line : game.getChecker())
            for(Piece pi : line)
                if(pi != null)
                    image(tabImg[pi.getImg()], pi.getX()* w/C, pi.getY() * h/C);
        
        //boolean[][] mouvs = game.getMoves(game.getTurn()%2 == 1, game.getChecker());
        if(game.getSelection() != null)
        //{
            for(int i = 0; i < pMoves.length; i++)
                for(int j = 0; j < pMoves[i].length; j++)
                //{
                    if(pMoves[i][j])
                    {
                        se.g.noStroke();
                        if(!game.getSelection().getTeam())
                            se.g.fill(0, 150, 0, 200);
                        else
                            se.g.fill(150, 0, 0, 200);
                        ellipse(j * w/C + w/C/2, i * w/C + w/C/2, 30, 30);
                    }
                    /*if(mouvs[i][j])
                    {
                        se.g.noStroke();
                        if(!game.getSelection().getTeam())
                            se.g.fill(0, 150, 0, 50);
                        else
                            se.g.fill(150, 0, 0, 50);
                        ellipse(j * w/C + w/C/2, i * w/C + w/C/2, 50, 50);
                    }
                }
        }*/
        
    }
    
    @Override
    public void mousePressed(int x, int y)                                      //Gestion des clics de souris
    {
        Promotion promo = (Promotion)game.getGO()[6];
        if(!promo.isVisible())
        {
            int rx = getRX(x), ry = getRY(y);                                   //On récupère les coordonnées de la souris relativement au GO
            Piece selec = game.getSelection();                                  //On récupère la pièce sélectionnée
            Piece nSelec = game.getChecker()[ry*C / h][rx*C / w];               //On récupère la pièce sous la souris
            if(selec == null)                                                   //Si il n'y a pas de pièce sélectionnée
            {
                changeSelection(nSelec);                                        //On modifie (éventuellement) la sélection
                return;                                                         //On s'arrête ici
            }
            pMoves = selec.getMoves(game.getChecker(), true);                   //On récupère les mouvements potentiels de la sélection
            if(pMoves[ry*C / h][rx*C / w])                                      //Si le clic est sur une case où le mouvements est autorisé...
            {
                selec.move(rx*C / w, ry*C / h, game.getChecker());              //On déplace la pièce sur la bonne case
                game.promotion(selec);                                          //Tentative de promotion
                game.setTurn();                                                 //On passe au tour suivant
                game.setSelection(null);                                        //On annule la sélection
            }
            else
                changeSelection(nSelec);                                        //On modifie la sélection
        }
    }

    @Override
    public void mouseMoved(int x, int y) {}
}
