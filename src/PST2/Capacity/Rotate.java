/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PST2.Capacity;

import PST2.Piece.Piece;

/**
 *
 * @author mad
 */
public class Rotate extends Capacity{

    public Rotate(Piece piece, int cooldown, int cast, int id, boolean active) {
        super(piece, cooldown, cast, id, active);
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
