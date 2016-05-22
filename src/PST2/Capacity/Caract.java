package PST2.Capacity;

import PST2.Piece.Piece;
import java.util.ArrayList;

public class Caract extends Capacity {

    private static final int COOL = 0;                                            //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                            //Temps de Cast propre à chaque capacité
    private final double mult;
    private final boolean up;
    private ArrayList<Piece> list = new ArrayList<>();
    private ArrayList<Integer[]> plus = new ArrayList<>();

    public Caract(Piece piece, boolean up) {
        super(piece, COOL, CAST, false);
        if (up)
            mult = 1.20;
        else
            mult = 0.75;
        this.up = up;
        init();
    }

    @Override
    public void power() {
        ArrayList<Piece> again = getAround(piece.getX(),piece.getY());
        for (Piece p : again)
            if (!list.contains(p))
                if ((up && (piece.getTeam() == p.getTeam())) || (!up && (piece.getTeam() != p.getTeam())))
                    set(p,again.indexOf(p));
        ArrayList<Integer[]> toDel=new ArrayList<>();
        for (Piece p : list)
            if (!again.contains(p))
                if ((up && (piece.getTeam() == p.getTeam())) || (!up && (piece.getTeam() != p.getTeam()))) {
                    remove(p,toDel);
                }
        plus.removeAll(toDel);
        list = again;
    }

    private void set(Piece p, int index) {
        Integer[] tab = new Integer[3];
        tab[0] = (int) ((double) (p.getAtt() * mult)-p.getAtt());
        tab[1] = (int) ((double) (p.getDef() * mult)-p.getDef());
        tab[2] = (int) ((double) (p.getLife() * mult)-p.getLife());
        plus.add(index,tab);
        p.setAtt(p.getAtt() + tab[0]);
        p.setDef(p.getDef() + tab[1]);
        p.setLife(p.getLife() + tab[2]);
    }

    private void remove(Piece p, ArrayList<Integer[]> toDel) {
        Integer[] tab = plus.get(list.indexOf(p));
        p.setAtt(p.getAtt() - tab[0]);
        p.setDef(p.getDef() - tab[1]);
        p.setLife(p.getLife() - tab[2]);
        toDel.add(plus.get(list.indexOf(p)));
    }

}
