package PST2.UI;

import PST2.*;
import PST2.Piece.Piece;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Description extends GraphicObject {

    private static final String path = "res/img/des/";
    private static final String fontP = "res/font/";
    private final Game g;
    private final PFont font;
    private final int size;
    private PImage i,j, k;
    private Piece p;
    private Button b1, b2;

    public Description(StratEdge se, int x, int y, int w, int h, String font, int size) {
        super(se, x, y, w, h, path);
        this.g = se.getGame();
        this.font = se.createFont(fontP + font, size);
        this.size = size;
    }

    @Override
    public void init() {
    }

    @Override
    public void draw() {

        se.g.imageMode(PApplet.CORNER);
        image(tabImg[12], 0, 0);                                                //Affiche le background
        if (g.getSelection() != null) {
            p = g.getSelection();
            i = tabImg[g.getSelection().getImg()];                              //Récupère l'image associée à la pièce selectionnée
            image(i, 20, 30);                                                   //Affiche l'image
            se.textFont(font);                                                  //Déclare la font et la taille de police à utiliser
            /*On ne fait pas appel à la méthode text de GraphicObject car
             * l'allignement se fait par la gauche et pas le centre comme 
             * dans celle-ci
             */
            se.textAlign(PApplet.LEFT);
            se.g.fill(0);
            se.g.text(p.getName(), x + i.width + 30, y + size + 10);  //Affiche le nom 10 px en dessous du haut du cadre
            se.g.text("Vie :" + p.getLife(),
                    x + i.width + 30, y + 2 * (size + 2) + 10);                         //Affiche la vie
            se.g.text("Attaque :" + p.getAtt(),
                    x + i.width + 30, y + 3 * (size + 2) + 10);                         //Affiche l'attaque
            se.g.text("Défense :" + p.getDef(),
                    x + i.width + 30, y + 4 * (size + 2) + 10);                         //Affiche la Defense
            j = tabImg[p.getCapacity1().getId() + 13];
            k = tabImg[p.getCapacity2().getId() + 13];
            image(j, 20, h / 3);
            image(k, 20, 2 * h / 3);
            se.g.text("Cooldown c1 :" + p.getCapacity1().getCurrentCool(), x + j.width + 30, y + h / 3 + 20);
            se.g.text("Cooldown c2 :" + p.getCapacity2().getCurrentCool(), x + k.width + 30, y + 2 * h / 3 + 20);
        }
        else {
            i=null;
            j = null;
            k = null;
            p = null;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (j != null && isIn(x, y,20,  + h / 3, 20 + j.width, + h / 3 + j.height))
            p.getCapacity1().power();
        else if (k != null && isIn(x, y, + 20, 2 * h / 3,  20 + k.width, 2 * h / 3 + k.height))
            p.getCapacity2().power();
    }

    private boolean isIn(int x, int y, int x1, int y1, int w, int h) {
        return (x >= (this.x +x1) && x <= (this.x + w) && y >= (this.y+y1) && y <= (this.y+h));
    }

    @Override
    public void mouseMoved(int x, int y) {
    }
}
