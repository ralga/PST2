package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;
import java.util.ArrayList;

public class Suicide extends Capacity {

    private static final int COOL = 0;                                          //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité
    private final int DAMAGE =50;

    public Suicide(Piece piece,int i) {
        super(piece, COOL, CAST,i, true);
        init();
    }

    @Override
    public void setfire() {
        Piece[][] checker = StratEdge.getSE().getGame().getChecker();
        ArrayList<Piece> around = getAround(piece.getX(), piece.getY());
        for (Piece p : around) {
            p.setLife(p.getLife() - DAMAGE);
            if (p.getLife() <= 0){
                p.kill();                
                checker[p.getY()][p.getX()]=null;
            }
        }
        piece.kill();
        StratEdge.getSE().getGame().setSelection(null);
        StratEdge.getSE().getGame().setTurn();
    }

    @Override
    protected void reset() {}

}
