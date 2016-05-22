package PST2.Capacity;

import PST2.Piece.Piece;

public class Suicide extends Capacity{

    private static final int COOL = 0;                                          //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité
    
    public Suicide(Piece piece) {
        super(piece, COOL, CAST, true);
        init();
    }

    @Override
    public void power() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
