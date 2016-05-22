package PST2.Piece;

import PST2.IO.Read;

import static PST2.Game.C;

/*Cette classe permet de créer une pièce avec la majorité de ses attributs*/

public class PieceImpl
{
    /*Constantes static*/
    protected static final int ROI = 0, REINE = 1, FOU = 2;                     //Types
    protected static final int CHEVALIER = 3, TOUR = 4, PION = 5;               // autorisées
    protected static final int TEAM1 = 0, TEAM2 = 1;                            //Teams autorisées
    private static final String MOVESFILE = "res/dat/piece/moves.txt";          //Fichier contenant les mouvements des différents types de pièces
    private static final String UMOVESFILE = "res/dat/piece/unitMoves.txt";     //Fichier contenant les mouvements unitaires
    private static final String PIECESFILE = "res/dat/piece/pieces.txt";        //Fichier contenant les données des différentes pièces
    private static final String NAMESFILE = "res/dat/piece/names.txt";          //Fichier contenant les noms des différentes pièces
    
    /*Variables static*/
    private static int[][] TABMOVES;                                            //Données contenues dans MOVESFILE
    private static int[][] TABUMOVES;                                           //Données contenues dans UMOVESFILE
    private static int[][] TABPIECES;                                           //Données contenues dans PIECESFILE
    private static String[] TABNAMES;                                           //Données contenues dans NAMESFILE
    
    /*Constantes non-static*/
    private final String NAME;                                                  //Nom de la pièce
    private final int[] moves;                                                  //Mouvements potentiellement autorisés (dépend de type)
    
    /*Variables non-static*/
    private int type;                                                           //Type de pièce : Roi:0; Reine:1; Fou:2; Chevalier:3; Tour:4; Pion:5.
    private int team;                                                           //Equipe à laquelle appartient la pièce : 0 équipe du haut
    private int image;                                                          //Index de l'image permettant de représenter la pièce sur le plateau de jeu
    private int attaque;                                                        //Attaque de la pièce (en nombre de pdv enlevé)
    private int defense;                                                        //Defense de la pièce
    private int life;                                                           //Nombre de pdv de la pièce
    private int x;                                                              //Coordonnées x de la pièce (en cellule)
    private int y;                                                              //Coordonnées y de la pièce (en cellule)
    private boolean alive;                                                      //Booléen qui indique si la pièce est en vie
    private boolean[][] pMoves = new boolean[C][C];                             //Mouvements possibles de la pièce
    
    /*Constructeur*/
    public PieceImpl(String NAME, int type, int team, int image, int attaque, int defense, int life, int x, int y)
    {
        this.NAME = NAME;
        this.type = type;
        this.team = team;
        this.image = image;
        this.attaque = attaque;
        this.defense = defense;
        this.life = life;
        this.x = x;
        this.y = y;
        alive = true;
        moves = TABMOVES[type];
    }
    
    /*Méthodes*/
    /**Charge les pièces et leurs mouvements
     * @param r L'objet Read qui nous permet de lire.*/
    public static void load(Read r)
    {
        TABMOVES = r.matrixTextFile(MOVESFILE);
        TABUMOVES = r.matrixTextFile(UMOVESFILE);
        TABPIECES = r.matrixTextFile(PIECESFILE);
        TABNAMES = r.file(NAMESFILE);
    }
    
    private void testDirection(int dir, int dist, int nUM, PieceImpl[][] checker)
    {
        if(nUM == 0)return;
        int xm = x + TABUMOVES[dir][0] * (dist-nUM+1);
        int ym = y + TABUMOVES[dir][1] * (dist-nUM+1);
        if(xm >= 0 && xm < C && ym >= 0 && ym < C)
        {
            if(checker[ym][xm] == null)
            {
                pMoves[ym][xm] = true;
                testDirection(dir, dist, nUM-1, checker);
            }
            else
            {
                pMoves[ym][xm] = checker[ym][xm].team != team;
                testDirection(dir, dist, 0, checker);
            }
        }
    }
    
    /*Getters*/
    public String getName(){return NAME;}
    public int getType(){return type;}
    public int getTeam(){return team;}
    public int getImg(){return image;}
    public int getAtt(){return attaque;}
    public int getDef(){return defense;}
    public int getLife(){return life;}
    public int getX(){return x;}
    public int getY(){return y;}
    public boolean isAlive(){return alive;}
    /**Renvoie les mouvements disponibles de la pièce.*/
    public boolean[][] getMoves(PieceImpl[][] checker)
    {
        pMoves = new boolean[C][C];
        for(int dir = 0; dir < moves.length; dir++)
            //if(!((type==5 && team==1 && dir==8) || (type==5 && team==0 && dir==0)))
            //if(!(type==5 && ((team==1 && dir==8)||(team==0 && dir==0))))
                testDirection(dir, moves[dir], moves[dir], checker);
        return pMoves;
    }
    public static int[][] getPieces(){return TABPIECES;}
    public static String[] getNames(){return TABNAMES;}
    
    /*Setters*/
    public void setType(int nType){type = nType;}
    public void setTeam(int nTeam){team = nTeam;}
    public void setImg(int nImg){image = nImg;}
    public void setAtt(int nAtt){attaque = nAtt;}
    public void setDef(int nDef){defense = nDef;}
    public void setLife(int nLife){life = nLife;}
    public void setX(int nX){x = nX;}
    public void setY(int nY){y = nY;}
    public void kill(){alive = false;}
}