package PST2.UI;

import PST2.Piece.Piece;
import PST2.Piece.Team;
import PST2.StratEdge;

public class DeadPieces extends GraphicObject
{

    private static final String path = "res/img/dead/";
    private final Team team;

    public DeadPieces(StratEdge se, int x, int y, int w, int h, Team t1)
    {
        super(se, x, y, w, h, DeadPieces.path);
        this.team = t1;
    }

    @Override
    public void init()
    {}

    @Override
    public void draw()
    {
        se.g.fill(150,100);
        rect(0, 0, w, h, 30);
        int i = 20, h = 5;
        for(Piece p : team.get())
        {
            if(!p.isAlive())
            {
                image(tabImg[p.getImg()], i, h);
                i += 55;
                if(i > 770)
                {
                    i = 20;
                    h = 60;
                }
            }
        }
    }

    @Override
    public void mousePressed(int x, int y)
    {}

    @Override
    public void mouseMoved(int x, int y)
    {}

}
