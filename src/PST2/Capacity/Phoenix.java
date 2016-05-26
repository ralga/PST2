package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;

/**
 *
 * @author mad
 */
public class Phoenix extends Capacity {

    private static final int COOL = 0;                                         //Cooldown propre à chaque capacité
    private final static int CAST = 0;                                          //Temps de Cast propre à chaque capacité
    private final int lifemax;

    public Phoenix(Piece piece,int id) {
        super(piece, COOL, CAST, id, false);
        lifemax = piece.getLife();
        init();
    }

    @Override
    public void setfire() {
        if (!piece.isAlive()){
            Piece[][] checker = StratEdge.getSE().getGame().getChecker();
            piece.setLife(lifemax / 4);
            if(piece.getLife()==0);
                piece.setLife(1);
            int[] pos = getNearest(checker,piece.getX(),piece.getY());
            piece.setPos(pos[0], pos[1]);
            checker[piece.getY()][piece.getX()]=piece;
            piece.getCapacity1().set();
            piece.getCapacity2().set();
            cooldown=COOL;
            piece.revive();
            StratEdge.getSE().getGame().promotion(piece);
        }
    }
    
    @Override
    public void power(){
        if(isAvailable() && !piece.isAlive())
            setfire();
        if(!(piece.isAlive() || isAvailable()))
            kill();
    }

    private int[] getNearest(Piece[][] checker,int x, int y) {
        if (checker[x][y] == null)
            return new int[]{x, y};
        else {
            int i = 0, j = 0;
            while (i < 10){
                for (int i2 = x - 1 - i; i2 <= x + 1 + i; ++i2)
                    for (int j2 = y - 1 - j; j2 <= y + 1 + j; ++j2) {
                        try{
                        if(checker[i2][j2]==null)
                            return new int[]{j2,i2};
                        }
                        catch(ArrayIndexOutOfBoundsException e){
                            
                        }
                    }
                ++i;
                ++j;
            }
        }
        return null;
    }

    @Override
    protected void reset() {
        cooldown=-1;
    }

}
