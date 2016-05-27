/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;
import java.util.ArrayList;

/**
 *
 * @author mad
 */
public class Reset extends Capacity{
    
    private static final int COOL = 10;                                         //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité

    public Reset(Piece piece, int id) {
        super(piece, COOL, CAST, id, true,"Recharge des capacités");
        init();
    }

    @Override
    public void setfire() {
        ArrayList<Capacity> pass = getPassive(piece.getTeam());
        ArrayList<Capacity> act = getActive();
        
        for(Capacity p : pass)
            p.setCool(0);
        for(Capacity p : act)
            if(p.piece.getTeam()==piece.getTeam())
                p.setCool(0);
        StratEdge.getSE().getGame().setSelection(null);
        StratEdge.getSE().getGame().setTurn();
    }

    @Override
    protected void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
