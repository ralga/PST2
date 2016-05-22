package PST2.Piece;

import PST2.Capacity.Capacity;
import PST2.Game;
import PST2.IO.Read;
import PST2.StratEdge;
import static PST2.Game.C;

public class SEPiece implements Piece
{
    /*Variables static*/
    private static int[][] TABMOVES;                                            //Données contenues dans MOVESFILE
    private static int[][] TABUMOVES;                                           //Données contenues dans UMOVESFILE
    private static int[][] TABPIECES;                                           //Données contenues dans PIECESFILE
    private static String[] TABNAMES;                                           //Données contenues dans NAMESFILE
    
    /*Constantes non-static*/
    private final String NAME;                                                  //Nom de la pièce
    protected int[] moves;                                                      //Mouvements potentiellement autorisés (dépend de type)
    
    /*Variables non-static*/
    private int type;                                                           //Type de pièce : Roi:0; Reine:1; Fou:2; Chevalier:3; Tour:4; Pion:5.
    private boolean team;                                                       //Equipe à laquelle appartient la pièce : false équipe du haut
    private int image;                                                          //Index de l'image permettant de représenter la pièce sur le plateau de jeu
    private int attack;                                                         //Attaque de la pièce (en nombre de pdv enlevé)
    private int defense;                                                        //Defense de la pièce
    private int life;                                                           //Nombre de pdv de la pièce
    private int x;                                                              //Coordonnées x de la pièce (en cellule)
    private int y;                                                              //Coordonnées y de la pièce (en cellule)
    private boolean alive = true;                                               //Booléen qui indique si la pièce est en vie
    private Capacity c1,c2;                                                     //Les deux capacités possédées par la pièce
    protected boolean firstMove = false;                                        //Détermine si le premier mouvement a été effectué
//    protected boolean[][] pMoves = new boolean[C][C];                           //Mouvements possibles de la pièce
    
    /*Constructeur*/
    public SEPiece(String NAME, int type, boolean team, int image, int attack, int defense, int life, int x, int y, int cap1, int cap2)
    {
        this.NAME = NAME;
        this.type = type;
        this.team = team;
        this.image = image;
        this.attack = attack;
        this.defense = defense;
        this.life = life;
        this.x = x;
        this.y = y;
        moves = TABMOVES[type].clone();
        this.c1 = Capacity.getCapacity(this, cap1);
        this.c2= Capacity.getCapacity(this, cap2);
    }
    
    public SEPiece(String NAME, int type, boolean team, int image, int x, int y, int cap1, int cap2)//Pièce classique
    {
        this(NAME, type, team, image, 1, 0, 1, x, y, cap1, cap2);
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
    
    @Override
    public void testDirection(int dir, int dist, int nUM, Piece[][] checker, boolean[][] pMoves)
    {
        if(nUM == 0)return;
        int xm = x + TABUMOVES[dir][0] * (dist-nUM+1);
        int ym = y + TABUMOVES[dir][1] * (dist-nUM+1);
        if(xm >= 0 && xm < C && ym >= 0 && ym < C)
        {
            if(checker[ym][xm] == null)
            {
                pMoves[ym][xm] = true;
                testDirection(dir, dist, nUM-1, checker, pMoves);
            }
            else
            {
                pMoves[ym][xm] = checker[ym][xm].getTeam() != team;
                testDirection(dir, dist, 0, checker, pMoves);
            }
        }
    }
    
    @Override
    public void saveTheKing(boolean[][] pMoves)
    {
        Game g = StratEdge.getSE().getGame();
        Piece[][] fChecker;
        for(int i = 0; i < pMoves.length; i++)                                  
            for(int j = 0; j < pMoves[i].length; j++)
                if(pMoves[i][j])
                {
                    fChecker = g.cloneChecker();                                //Clonage du checker <=> fChecker est virtuel : on peut faire ce que l'on veut dessus
                    fChecker[y][x].move(j, i, fChecker);                        //On effectue un mouvement virtuel de la pièce sur la case (j, i)
                    if(g.isCheck(team, fChecker))                               //Si le Roi est virtuellement en échec
                        pMoves[i][j] = false;                                   //On empêche (réellement) la possibilité d'effectuer ce mouvement
                }
    }
    
    @Override
    public void move(int x, int y, Piece[][] checker)
    {
        checker[getY()][getX()] = null;                                         //On supprime la pièce de la case où elle se trouve
        if(checker[y][x]!=null)
        {
            Piece enemy = checker[y][x];
            life -= enemy.getDef();
            enemy.setLife(enemy.getLife() - attack);
            if(life > 0)
            {
                enemy.kill();
                setPos(x, y);                                                   //On modifie ses coordonnées
                checker[getY()][getX()] = this;                                 //On déplace la pièce sélectionnée sur la case sélectionnée
            }
            else if(enemy.getLife() <= 0)
            {
                enemy.kill();
                kill();
                checker[y][x] = null;
            }
            else
                kill();
        }
        else
        {
            setPos(x, y);                                                       //On modifie ses coordonnées
            checker[getY()][getX()] = this;                                     //On déplace la pièce sélectionnée sur la case sélectionnée
        }
    }
    
    /*Getters*/
    
    @Override
    public Piece clonePiece()
    {
        SEPiece p;
        switch(type)
        {
            case Piece.KING:
                p = new King(NAME, team, image, attack, defense, life, x, y,(-1),(-1));
                break;
            case Piece.PAWN:
                p = new Pawn(NAME, team, image, attack, defense, life, x, y, -1,-1);
                break;
            default:
                p = new SEPiece(NAME, type, team, image, attack, defense, life, x, y,(-1),-1);
                break;
        }
        p.firstMove = firstMove;
        p.alive = alive;
        p.moves = moves.clone();
        return p;
    }
    @Override
    public String getName(){return NAME;}
    @Override
    public int getType(){return type;}
    @Override
    public boolean getTeam(){return team;}
    @Override
    public int getImg(){return image;}
    @Override
    public int getAtt(){return attack;}
    @Override
    public int getDef(){return defense;}
    @Override
    public int getLife(){return life;}
    @Override
    public int getX(){return x;}
    @Override
    public int getY(){return y;}
    @Override
    public boolean isAlive(){return alive;}
    @Override
    public boolean getFM(){return firstMove;}
    @Override
    public boolean[][] getMoves(Piece[][] checker, boolean saveTheKing)
    {
        boolean[][] pMoves = new boolean[C][C];
        for(int dir = 0; dir < moves.length; dir++)
            testDirection(dir, moves[dir], moves[dir], checker, pMoves);
        if(saveTheKing)
            saveTheKing(pMoves);
        return pMoves;
    }
    
    @Override
    public Capacity getCapacity1() {return c1;}
    @Override
    public Capacity getCapacity2() {return c2;}
    
    public static int[][] getPieces(){return TABPIECES;}
    
    public static String[] getNames(){return TABNAMES;}
    
    /*Setters*/
    
    @Override
    public void setType(int nType){type = nType;}
    @Override
    public void setTeam(boolean nTeam){team = nTeam;}
    @Override
    public void setImg(int nImg){image = nImg;}
    @Override
    public void setAtt(int nAtt){attack = nAtt;}
    @Override
    public void setDef(int nDef){defense = nDef;}
    @Override
    public void setLife(int nLife){life = nLife;}
    @Override
    public void setPos(int nX, int nY)
    {
        x = nX; 
        y = nY;
        if(nX != 0 || nY != 0)
            firstMove = true;                                                   //Permet de savoir si le pion s'est déjà déplacé
    }
    
    @Override
    public void kill(){alive = false;}


}