package PST2.Capacity;

import PST2.Piece.Piece;

public class Rotate extends Capacity{

    private static final int COOL = 0;                                         //Cooldown propre à chaque capacité
    private final static int CAST = 7;                                          //Temps de Cast propre à chaque capacité
    
    public Rotate(Piece piece, int cooldown, int cast, int id, boolean active) {
        super(piece, COOL, CAST, id, active,"Rotation du plateau");
    }

    @Override
    public void setfire() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
