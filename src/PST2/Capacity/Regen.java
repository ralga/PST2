package PST2.Capacity;

import PST2.Piece.Piece;

public class Regen extends Capacity {

    private static final int COOL = 0;                                            //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                            //Temps de Cast propre à chaque capacité
    private final int REGEN = 1;
    private final int lifemax;

    public Regen(Piece p,int i) {
        super(p,COOL, CAST,i, false, "Régénération");
        lifemax = p.getLife();
        this.isActive=false;
        init();
    }

    @Override
    public void setfire() {
        if (piece.isAlive() && isAvailable()){
            if (piece.getLife() < lifemax - REGEN)
                piece.setLife(piece.getLife() + REGEN);
            else if (piece.getLife() != lifemax)
                piece.setLife(lifemax);
            cooldown=COOL;
        }
    }

    @Override
    protected void reset() {}

}
