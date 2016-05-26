package PST2.Piece;

import static PST2.Game.C;
import PST2.StratEdge;

public class King extends SEPiece
{
    private boolean check = false;                                              //Détermine si le roi est en échec
            
    public King(String NAME, boolean team, int image, int attack, int defense, int life, int x, int y, int cap1, int cap2)
    {
        super(NAME, KING, team, image, attack, defense, life, x, y, cap1, cap2);
    }
    
    @Override
    public boolean[][] getMoves(Piece[][] checker, boolean saveTheKing)         //Gestion des mouvements particuliers du roi
    {
        boolean[][] pMoves = super.getMoves(checker, saveTheKing);              //On remplie pMoves de manière classique
        
        if(!firstMove && !check)                                                //Si le roi n'a pas bougé et n'est pas en échec
        {
            boolean canRoque = false;                                           //Variable qui détermine si le roque est autorisé
            
            //Tour gauche (grand roque)
            if(checker[getY()][0] != null)                                      //Si la case de la tour gauche est non vide
                canRoque = !checker[getY()][0].getFM();                         //On vérifie que la pièce sur la case n'a pas encore bougé (<=> c'est bien la tour gauche)
            for(int i = 1; i < 4; i++)                                          //On vérifie que les cases entre la tour et le roi sont vide
                canRoque &= checker[getY()][i] == null;
            if(canRoque)                                                        //Si le roque est possible
                pMoves[getY()][2] = true;                                       //Modification dans pMoves de la case sur laquelle on peut désormais déplacer le roi
            
            //Tour droite (petit roque)
            canRoque = false;
            if(checker[getY()][C-1] != null)                                    //Si la case de la tour droite est non vide
                canRoque = !checker[getY()][C-1].getFM();                       //On vérifie que la pièce sur la case n'a pas encore bougé (<=> c'est bien la tour droite)
            for(int i = 5; i < 7; i++)                                          //On vérifie que les cases entre la tour et le roi sont vide
                canRoque &= checker[getY()][i] == null;
            if(canRoque)                                                        //Si le roque est possible
                pMoves[getY()][6] = true;                                       //Modification dans pMoves de la case sur laquelle on peut désormais déplacer le roi
        }
        
        return pMoves;
    }
    
    @Override
    public void setPos(int nX, int nY)
    {
        if(Math.abs(nX-getX()) == 2)                                            //Si le roi est déplacé de 2 cases de sa position (<=> roque)
        {
            int xR, nxR;                                                        //Respectivement la position x de départ de la tour qui roque et celle d'arrivée
            Piece[][] checker = StratEdge.getSE().getGame().getChecker();
            if(nX-getX() < 0)                                                   //Si grand roque
            {
                xR = 0;
                nxR = 3;
            }
            else                                                                //Sinon (<=> petit roque)
            {
                xR = C-1;
                nxR = 5;
            }
            checker[getY()][xR].setPos(nxR, getY());                            //On modifie ses coordonnées
            checker[getY()][nxR] = checker[getY()][xR];                         //On déplace la pièce sélectionnée sur la case sélectionnée
            checker[getY()][xR] = null;                                         //On supprime la pièce sélectionnée de la case où elle se trouve
        }
        super.setPos(nX, nY);
    }
    
    public void setCheck(boolean nCheck){check = nCheck;}
}
