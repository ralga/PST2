package PST2.Piece;

import static PST2.Game.C;

public class Pawn extends SEPiece
{
    public Pawn(String NAME, boolean team, int image, int attack, int defense, int life, int x, int y, int cap1, int cap2)
    {
        super(NAME, PAWN, team, image, attack, defense, life, x, y, cap1, cap2);
        if(team)                                                                //Empêche le pion de revenir sur ses pas
            moves[8] = 0;
        else
            moves[0] = 0;
    }
    
    private boolean isOnChecker(int x, int y)                                   //Vérifie que la position x, y est bien sur le terrain
    {
        return x >= 0 && x < C && y >= 0 && y < C;
    }
    
    @Override
    public boolean[][] getMoves(Piece[][] checker, boolean saveTheKing)         //Gestion des mouvements particuliers du pion
    {
        if(moves[0] == 2 && firstMove && getTeam())                             //Empêche de bouger de 2 cases en un tour après le premier mouv, pour la team du bas
            moves[0] = 1;
        else if(moves[8] == 2 && firstMove && !getTeam())                       //Idem avec la team du haut
            moves[8] = 1;
        
        boolean[][] pMoves = super.getMoves(checker, false);                    //On remplie pMoves de manière classique
        
        int s = getTeam() ? -1 : 1;                                             //Optimisation pour ne pas avoir à réécrire 2 fois le même code en fonction de l'équipe
        if(isOnChecker(getX(), getY()+s))
            if(checker[getY()+s][getX()] != null)                               //Empêche de manger en ligne droite sur la 1ère case
                pMoves[getY()+s][getX()] = false;
        if(isOnChecker(getX(), getY()+2*s))
            if(!firstMove && checker[getY()+2*s][getX()] != null)               //Idem sur la 2ème case
                pMoves[getY()+2*s][getX()] = false;
        
        if(isOnChecker(getX()-1, getY()+s))
            if(checker[getY()+s][getX()-1] != null)                             //Permet de manger en diagonale à gauche
                if(checker[getY()+s][getX()-1].getTeam() != getTeam())
                    pMoves[getY()+s][getX()-1] = true;
        if(isOnChecker(getX()+1, getY()+s))
            if(checker[getY()+s][getX()+1] != null)                             //Idem à droite
                if(checker[getY()+s][getX()+1].getTeam() != getTeam())
                    pMoves[getY()+s][getX()+1] = true;
        
        if(saveTheKing)                                                         
            saveTheKing(pMoves);                                                //On supprime les mouvements qui trahissent le roi

        return pMoves;
    }
}
