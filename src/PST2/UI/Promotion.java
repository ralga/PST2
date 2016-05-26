package PST2.UI;

import PST2.StratEdge;
import processing.core.*;

import static PST2.UI.Checker.W;
import static PST2.Game.C;
import PST2.Piece.*;

public class Promotion extends GraphicObject {

    private static final String IMGPATH = "res/img/promo/";
    private static final int R = 105;
    private static final int MINR = 35;
    private final int cx, cy;
    private boolean visible = false;                                            //Détermine s'il faut activer l'affichage et les événements du composant
    private Pawn p;

    public Promotion(StratEdge se, int x, int y, int cx, int cy) {
        super(se, x, y, R, IMGPATH);
        this.cx = cx - (se.width - se.getW()) / 2;
        this.cy = cy - (se.height - se.getH()) / 2;
    }

    public void promote(Pawn p) {
        this.p = p;
        setX(cx + p.getX() * W / C + W / C / 2);
        setY(cy + p.getY() * W / C + W / C / 2);
        visible = true;
    }

    @Override
    public void init() {
    }

    @Override
    public void draw() {
        if (visible) {
            se.g.imageMode(PApplet.CENTER);
            se.g.tint(255, 200);
            image(tabImg[1], 0, 0);
            se.g.noTint();
        }
    }

    @Override
    public void mousePressed(int mx, int my) {
        if (visible) {
            mx = getRX(mx);
            my = -getRY(my);
            if (Math.sqrt(mx * mx + my * my) >= MINR) {
                double norme = Math.sqrt(mx * mx + my * my);
                double angle = Math.acos(mx / norme);
                int type;
                float life = p.getLife();
                float att = p.getAtt();
                float def = p.getDef();
                if (angle <= Math.PI / 4) {
                    type = Piece.ROOK;
                    life *= 1.5;
                    att *= 1.5;
                    def *= 3;
                }
                else if (angle >= Math.PI * 3 / 4) {
                    type = Piece.KNIGHT;
                    life *= 1.2;
                    att *= 3;
                    def *= 1.5;
                }
                else if (my > 0 && angle >= Math.PI / 4 && angle <= Math.PI * 3 / 4) {
                    type = Piece.QUEEN;
                    life *= 2;
                    att *= 2;
                    def *= 2;
                }
                else {
                    type = Piece.BISHOP;
                    life *= 1.5;
                    att *= 1.75;
                    def *= 1.75;

                }

                System.out.println(type);

                SEPiece np = new SEPiece(p.getName(), type, p.getTeam(), p.getImg(), (int) att, (int) def, (int) life, p.getX(), p.getY(), -1, -1);
                np.setCapacity1(p.getCapacity1());
                np.setCapacity2(p.getCapacity2());
                np.pawn(p);
                se.getGame().getChecker()[p.getY()][p.getX()] = np;

                visible = false;
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
    }

    /*Getters*/
    public boolean isVisible() {
        return visible;
    }

}
