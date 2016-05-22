package PST2.Piece;

import PST2.Capacity.Capacity;

public interface Piece
{
    public static final int KING = 0, QUEEN = 1, BISHOP = 2;                    //Types
    public static final int KNIGHT = 3, ROOK = 4, PAWN = 5;                     // autorisées
    public static final boolean TEAM1 = false, TEAM2 = true;                    //Teams autorisées : false = team du haut !
    public static final String MOVESFILE = "res/dat/piece/moves.txt";           //Fichier contenant les mouvements des différents types de pièces
    public static final String UMOVESFILE = "res/dat/piece/unitMoves.txt";      //Fichier contenant les mouvements unitaires
    public static final String PIECESFILE = "res/dat/piece/pieces.txt";         //Fichier contenant les données des différentes pièces (type vie attaque defense)
    public static final String NAMESFILE = "res/dat/piece/names.txt";           //Fichier contenant les noms des différentes pièces
    
    public void testDirection(int dir, int dist, int nUM, Piece[][] checker, boolean[][] pMoves);   
    public void move(int x, int y, Piece[][] checker);                          //Déplace la pièce sur le terrain checker aux coordonnées (x, y)
    public void saveTheKing(boolean[][] pMoves);                                //Supprime des déplacements lorsque le roi est en échec
    
    /*Getters*/
    public String getName();
    public int getType();
    public boolean getTeam();
    public int getImg();
    public int getAtt();
    public int getDef();
    public int getLife();
    public int getX();
    public int getY();
    public boolean isAlive();
    public boolean getFM();
    public boolean[][] getMoves(Piece[][] checker, boolean saveTheKing);
    public Piece clonePiece();
    public Capacity getCapacity1();
    public Capacity getCapacity2();
    
    /*Setters*/
    public void setType(int nType);
    public void setTeam(boolean nTeam);
    public void setImg(int nImg);
    public void setAtt(int nAtt);
    public void setDef(int nDef);
    public void setLife(int nLife);
    public void setPos(int nX, int nY);                                         //Modifie la position de la pièce sur (nX, nY) et vérifie que le 1er mouvement n'a pas été effectué
    public void kill();
}
