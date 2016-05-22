package PST2.Piece;

import static PST2.Game.C;

public class Team
{
    private Piece team[] = new Piece[2*C];
    private King king;

    public Team(int[] team, boolean side)
    {
        int id, y;
        int[][] pcs = SEPiece.getPieces();
        String[] ns = SEPiece.getNames();
        for(int i = 0; i < team.length; i++)
        {
            id = team[i];
            y = side ? (C - 1) - i / C : i / C;
            switch(pcs[id][0])
            {
                case Piece.KING:
                    this.team[i] = king = new King(ns[id], side, id, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y,2,1);
                    break;
                case Piece.PAWN:
                    this.team[i] = new Pawn(ns[id], side, id, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y,0,1);
                    break;
                default:
                    this.team[i] = new SEPiece(ns[id], pcs[id][0], side, id, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y,0,1);
                    break;
            }
        }
    }

    /*Getters*/
    public Piece[] get(){return team;}                                          //Renvoie les pièces de la team
    public Piece get(int i){return team[i];}                                    //Renvoie la pièce d'index i
    public King getKing(){return king;}                                         //Renvoie le roi de l'équipe
}
