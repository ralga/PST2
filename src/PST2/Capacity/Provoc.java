package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;
import PST2.UI.Button;
import java.util.ArrayList;

public class Provoc extends Capacity{

    private static final int COOL = 3;                                          //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité
    
    public Provoc(Piece piece,int id) {
        super(piece, COOL, CAST, id, true);
        init();
    }

    @Override
    public void setfire() {
        int cx = StratEdge.getSE().getViews()[1].getGO()[3].getX();
        int cy = StratEdge.getSE().getViews()[1].getGO()[3].getY();
        int i =3;
        ArrayList<Piece> around = getAround(piece.getX(),piece.getY());
        ArrayList<Button> but = new ArrayList<>();
        for (Piece p : around) {
            but.add(new Button(StratEdge.getSE(),p.getX()*70 +cx,p.getY()*70+cy,70,70,i,0));
            ++i;
        }
        
    }

    @Override
    protected void reset() {}
    
}
