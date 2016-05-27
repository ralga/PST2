package PST2.Capacity;

import PST2.Piece.Piece;
import java.util.ArrayList;

public class Caract extends Capacity {

    private static final int COOL = 0;                                          //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité
    private final double mult;
    private final double div;
    private final boolean up;
    private ArrayList<Piece> ancien = new ArrayList<>();

    public Caract(Piece piece, int i,boolean up, String name) {
        super(piece, COOL, CAST,i, false, name);
        if (up) {
            mult = 1.2;
            div = 0.83333;
        }
        else {
            mult = 0.75;
            div = 1.4;
        }
        this.up = up;
        init();
    }

    @Override
    public void setfire() {
        if (isAvailable()) {
            ArrayList<Piece> again = getAround(piece.getX(), piece.getY());
            for (Piece p : again)
                if (!ancien.contains(p))
                    if ((up && (piece.getTeam() == p.getTeam())) || (!up && (piece.getTeam() != p.getTeam())))
                        set(p);
            for (Piece p : ancien)
                if (!again.contains(p))
                    if ((up && (piece.getTeam() == p.getTeam())) || (!up && (piece.getTeam() != p.getTeam())))
                        reset(p);
            ancien = again;
        }
    }

    private void set(Piece p) {
        p.setAtt((int) ((double) Math.round((p.getAtt() * mult))));
        p.setDef((int) ((double) Math.round((p.getDef() * mult))));
        p.setLife((int) ((double) Math.round((p.getLife() * mult))));
    }

    private void reset(Piece p) {
        p.setAtt((int) Math.round((double) (p.getAtt() * div)));
        p.setDef((int) Math.round((double) (p.getDef() * div)));
        p.setLife((int) Math.round((double) (p.getLife() * div)));
    }

    @Override
    protected void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
