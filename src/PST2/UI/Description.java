package PST2.UI;

import PST2.*;
import PST2.Capacity.Capacity;
import PST2.Piece.Piece;
import PST2.Piece.SEPiece;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Description extends GraphicObject {

    private static final String path = "res/img/des/";
    private static final String fontP = "res/font/";
    private final Game g;
    private final PFont font;
    private final int size;
    private PImage i, j, k;
    private Piece p;
    private final boolean isNormal;

    public Description(StratEdge se, int x, int y, int w, int h, String font, int size) {
        super(se, x, y, w, h, path);
        this.g = se.getGame();
        this.font = se.createFont(fontP + font, size);
        this.size = size;
        isNormal = !(Capacity.getPassive(true).isEmpty() && Capacity.getPassive(false).isEmpty() && Capacity.getActive().isEmpty());

    }

    @Override
    public void draw() {

        se.g.imageMode(PApplet.CORNER);
        image(tabImg[36], 0, 0);                                                //Affiche le background
        if (g.getSelection() != null) {
            p = g.getSelection();
            i = tabImg[g.getSelection().getImg()];                              //Récupère l'image associée à la pièce selectionnée
            image(i, 30, 30);                                                   //Affiche l'image
            se.textFont(font);                                                  //Déclare la font et la taille de police à utiliser
            /*On ne fait pas appel à la méthode text de GraphicObject car
             * l'allignement se fait par la gauche et pas le centre comme 
             * dans celle-ci
             */
            se.textAlign(PApplet.LEFT);
            se.g.fill(0);
            se.g.text(p.getName(), x + i.width + 30, y + size + 20);  //Affiche le nom 10 px en dessous du haut du cadre
            se.g.text("Vie : " + p.getLife() + " / " + p.getLifemax(),
                    x + i.width + 30, y + 2 * (size + 2) + 15);                         //Affiche la vie
            se.g.text("Attaque :" + p.getAtt(),
                    x + i.width + 30, y + 3 * (size + 2) + 10);                         //Affiche l'attaque
            se.g.text("Défense :" + p.getDef(),
                    x + i.width + 30, y + 4 * (size + 2) + 10);                         //Affiche la Defense
            if (isNormal) {
                j = tabImg[p.getCapacity1().getId() + 37];
                k = tabImg[p.getCapacity2().getId() + 37];
                image(j, 30, h / 3);
                image(k, 30, 2 * h / 3);
                se.g.text(p.getCapacity1().getName(), x + j.width + 50, y + h / 3 + 20);
                if (p.getCapacity1().getCurrentCool()!=0)
                    se.g.text("Cooldown c1 :" + p.getCapacity1().getCurrentCool(), x + j.width + 50, y + h / 3 + 55);
                se.g.text(p.getCapacity2().getName(), x + k.width + 50, y + 2 * h / 3 + 20);
                if (p.getCapacity2().getCurrentCool()!=0)
                    se.g.text("Cooldown c2 :" + p.getCapacity2().getCurrentCool(), x + k.width + 50, y + 2 * h / 3 + 55);
            }
        }
        else {
            i = null;
            j = null;
            k = null;
            p = null;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (j != null && p.getCapacity1().isActive() && isIn(x, y, 20, +h / 3, 20 + j.width, +h / 3 + j.height))
            p.getCapacity1().power();
        else if (k != null && p.getCapacity2().isActive() && isIn(x, y, +20, 2 * h / 3, 20 + k.width, 2 * h / 3 + k.height))
            p.getCapacity2().power();
    }

    private boolean isIn(int x, int y, int x1, int y1, int w, int h) {
        return (x >= (this.x + x1) && x <= (this.x + w) && y >= (this.y + y1) && y <= (this.y + h));
    }

    @Override
    public void mouseMoved(int x, int y) {
    }

    @Override
    public void init() {
    }
}
