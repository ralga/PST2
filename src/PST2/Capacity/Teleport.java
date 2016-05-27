package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;

public class Teleport extends Capacity {
    
    private static final int COOL=5;                                             //Cooldown propre à chaque capacité
    private final static int CAST=0;                                             //Temps de Cast
    private Piece[][] tab;

    public Teleport(Piece p,int i) {
        super(p,COOL, CAST,i,true, "Téléportation");
        this.isActive=true;
        init();
    }

    @Override
    public void setfire() {
        if (isAvailable()) {        
            tab=StratEdge.getSE().getGame().getChecker(); 
//            boolean[][] pmoves=getmoves(tab);
//            piece.saveTheKing(pmoves);
            System.out.println("FullPower !");
            cooldown = COOL;
        }
    }

    private boolean[][] getmoves(Piece[][] tab){                                //Retourne un tableau indiquant quelles cases sont vides
        boolean[][] pmoves = new boolean[tab.length][tab.length];
        for(int i=0;i<tab.length;++i)
            for(int j=0;i<tab.length;++j)
                pmoves[i][j]=(tab[i][j]==null);
        return pmoves;
    }

    @Override
    protected void reset() {}

}
