package PST2.Piece;

import static PST2.Game.C;

public class Team
{
    private Piece team[] = new Piece[2*C];
    private King king;
    private final boolean side;

    public Team(int[] team, boolean side)
    {
        this.side = side;
        int id, y, img;
        int[][] pcs = SEPiece.getPieces();
        String[] ns = SEPiece.getNames();
        for(int i = 0; i < team.length; i++)
        {
            id = team[i];
            y = side ? (C - 1) - i / C : i / C;
            img = id * 2 + (side ? 0 : 1);
            switch(pcs[id][0])
            {
                case Piece.KING:
                    this.team[i] = king = new King(ns[id], side, img, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y, pcs[id][4], pcs[id][5]);
                    break;
                case Piece.PAWN:
                    this.team[i] = new Pawn(ns[id], side, img, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y, pcs[id][4], pcs[id][5]);
                    break;
                default:
                    this.team[i] = new SEPiece(ns[id], pcs[id][0], side, img, pcs[id][1], pcs[id][2], pcs[id][3], i % C, y, pcs[id][4], pcs[id][5]);
                    break;
            }
        }
    }

    /*Getters*/
    public Piece[] get(){return team;}                                          //Renvoie les pièces de la team
    public King getKing(){return king;}                                         //Renvoie le roi de l'équipe
    public boolean getSide(){return side;}
}
