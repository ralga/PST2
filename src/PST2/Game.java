package PST2;

import PST2.Capacity.Capacity;
import PST2.Online.Connexion;
import PST2.Piece.*;
import PST2.UI.*;

public class Game extends View {

    public static final int C = 8;                                              //Nombre de cases sur une ligne / colonne

    private int turn = 0;                                                       //Tour actuel -> détermine qui doit jouer
    private Piece[][] checker;                                                  //Terrain : case vide : null; case non vide : Piece qui est dessus
    private Piece selection = null;                                             //Pièce sélectionnée
    public Team t1, t2;

    public void init(Team t1, Team t2) {
        checker = new Piece[C][C];
        this.t1 = t1;
        this.t2 = t2;
        for (Piece p1 : t1.get())
            checker[p1.getY()][p1.getX()] = p1;
        for (Piece p2 : t2.get())
            checker[p2.getY()][p2.getX()] = p2;
    }

    public void start(int te1, int te2) {
        Capacity.getActive().removeAll(Capacity.getActive());
        Capacity.getPassive(true).removeAll(Capacity.getPassive(true));
        Capacity.getPassive(false).removeAll(Capacity.getPassive(false));
        Team team1 = new Team(SEPiece.getTeams()[te1], Piece.TEAM1);            //Création de l'équipe 1
        Team team2 = new Team(SEPiece.getTeams()[te2], Piece.TEAM2);            //Création de l'équipe 2
        init(team1, team2);                                                     //Initialisation du checker
        StratEdge se = StratEdge.getSE();
        if (tabGO != null) //Si on a jamais lancé de partie
        {
            tabGO[1] = new DeadPieces(se, (se.getW() - Checker.W) / 4, 0, Checker.W, (se.getH() - Checker.W) / 4, t1);
            tabGO[2] = new DeadPieces(se, (se.getW() - Checker.W) / 4, (Checker.W + (se.getH() - Checker.W) / 4 * 3), Checker.W, (se.getH() - Checker.W) / 4, t2);
            tabGO[5] = new Description(se, (se.getW() - Checker.W) + 100, (se.getH() - Checker.W) / 2, 2 * Checker.W / 3, Checker.W, "OldLondon.ttf", 30);
            tabGO[9] = new Text(se, tabGO[3].getX(), 25, Checker.W, 100, 45, "", "OldLondon.ttf", 255);
            tabGO[10] = new Text(se, tabGO[3].getX(), Checker.W + 65, Checker.W, 100, 45, "", "OldLondon.ttf", 255);
            tabGO[11] = new Text(se, tabGO[5].getX(), Checker.W + 65, 300, 100, 25, "", "OldLondon.ttf", 200);
        }
        selection = null;
        turn = 0;

        StratEdge.getSE().setView(2);                                           //Changement de view : le jeu commence
    }

    @Override
    public void initGraphicObjects() {
        StratEdge se = StratEdge.getSE();
        tabGO = new GraphicObject[12];
        tabGO[0] = new Background(se, 1);
        tabGO[1] = new DeadPieces(se, (se.getW() - Checker.W) / 4, 0, Checker.W, (se.getH() - Checker.W) / 4, t1);
        tabGO[2] = new DeadPieces(se, (se.getW() - Checker.W) / 4, (Checker.W + (se.getH() - Checker.W) / 4 * 3), Checker.W, (se.getH() - Checker.W) / 4, t2);
        tabGO[3] = new Checker(se, (se.getW() - Checker.W) / 4, (se.getH() - Checker.W) / 2);
        tabGO[4] = new Armies(se, tabGO[3].getX(), tabGO[3].getY());
        tabGO[5] = new Description(se, (se.getW() - Checker.W) + 100, (se.getH() - Checker.W) / 2, 2 * Checker.W / 3, Checker.W, "OldLondon.ttf", 30);
        tabGO[6] = new Promotion(se, 0, 0, tabGO[3].getX(), tabGO[3].getY());
        tabGO[7] = new Debug(se, se.getW() - 80, 3, 70, 40);
        tabGO[8] = new Button(se, 10, 10, 0, 1, 2);
        tabGO[9] = new Text(se, tabGO[3].getX(), 25, Checker.W, 100, 45, "", "OldLondon.ttf", 255);
        tabGO[10] = new Text(se, tabGO[3].getX(), Checker.W + 65, Checker.W, 100, 45, "", "OldLondon.ttf", 255);
        tabGO[11] = new Text(se, tabGO[5].getX(), Checker.W + 65, 300, 100, 25, "", "OldLondon.ttf", 200);
    }

    public Piece[][] cloneChecker() //Crée une copie du checker
    {
        Piece[][] clone = new Piece[C][C];
        for (int i = 0; i < checker.length; i++)
            for (int j = 0; j < checker[i].length; j++)
                if (checker[i][j] != null)
                    clone[i][j] = checker[i][j].clonePiece();
        return clone;
    }

    public void promotion(Piece p) {
        if (p.getType() == Piece.PAWN && (p.getY() == 0 || p.getY() == C - 1)) {
            Promotion promo = (Promotion) (tabGO[6]);
            promo.promote((Pawn) p);
        }
    }

    /*Getters*/
    public Piece[][] getChecker() {
        return checker;
    }

    public Piece getSelection() {
        return selection;
    }

    public int getTurn() {
        return turn;
    }

    public boolean[][] getMoves(boolean team, Piece[][] checker) //Renvoie tous les mouvements pouvant être effectués par une équipe
    {
        boolean[][] moves = new boolean[C][C];                                  //Matrice : true mouvement autorisé; false sinon
        boolean[][] pmoves;
        for (Piece[] ligne : checker)                                            //On parcourt chacune des pièces du plateau
            for (Piece p : ligne)
                if (p != null && p.getTeam() == team) //On vérifie que la pièce est non nulle et dans la bonne équipe
                {
                    pmoves = p.getMoves(checker, false);                        //On récupère les mouvements autorisés de la pièce
                    for (int i = 0; i < pmoves.length; i++)                      //On parcourt chaque case du plateau
                        for (int j = 0; j < pmoves[i].length; j++)
                            moves[i][j] |= pmoves[i][j];                        //On ajoute les nouveaux mouvements autorisés
                }
        return moves;
    }

    public boolean isCheck(boolean team, Piece[][] checker) //Renvoie true si le roi de l'équipe team est en echec, false sinon
    {
        boolean[][] moves = getMoves(!team, checker);                           //Récupère les possibilités de mouvements adverses
        for (int i = 0; i < moves.length; i++)                                   //Pour chacune des cases du terrain
            for (int j = 0; j < moves[i].length; j++)
                if (moves[i][j] && checker[i][j] != null)                        //Si l'adversaire peut capturer la pièce sur la case
                    if (checker[i][j].getType() == Piece.KING)                   //Si la pièce sur la case est le roi
                        return true;                                            //Alors le roi est en échec
        return false;
    }

    public boolean isLock(boolean team) {
        boolean[][] pmoves;
        for (Piece[] ligne : checker)                                            //On parcourt chacune des pièces du plateau
            for (Piece p : ligne)
                if (p != null && p.getTeam() == team) //On vérifie que la pièce est non nulle et dans la bonne équipe
                {
                    pmoves = p.getMoves(checker, true);                         //On récupère les mouvements autorisés de la pièce
                    for (boolean[] bl : pmoves)                                  //On parcourt chaque case du plateau
                        for (boolean b : bl)
                            if (b)                                               //Si il existe un mouvement
                                return false;                                   //Il n'y a pas de blocage
                }
        return true;
    }

    /*Setters*/
    public void setSelection(Piece nSelec) {
        selection = nSelec;
    }

    public void setTurn() {
        t1.getKing().setCheck(false);
        t2.getKing().setCheck(false);
        turn++;
        boolean team = turn % 2 == 0;
        for (Capacity c : Capacity.getPassive(team)) {
            c.minusCool();
            c.power();
        }
        if (!team)
            for (Capacity c : Capacity.getActive())
                c.minusCool();

        Text t, tx1, tx2;
        t = (Text) tabGO[11];
        if (Connexion.state == 3 && t1.getSide() == team)
            t.setText("C'est à vous de jouer");
        else if (Connexion.state == 3 && t1.getSide() != team)
            t.setText("C'est à l'adversaire de jouer");

        tx1 = (Text) tabGO[9];
        tx2 = (Text) tabGO[10];
        if (!team)
            t = tx1;
        else
            t = tx2;
        if (isCheck(team, checker)) //Si le roi qui doit jouer est en échec
        
            if (isLock(team))
                t.setText("Echec et Mat !");
            else {
                t.setText("Echec !");
                if (!team)
                    t1.getKing().setCheck(true);
                else
                    t2.getKing().setCheck(true);
            }
        else if (isLock(team))
            t.setText("Pat !");
        else {
            tx1.setText("");
            tx2.setText("");
        }
    }
}
