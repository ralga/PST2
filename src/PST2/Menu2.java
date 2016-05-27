package PST2;

import PST2.UI.*;

public class Menu2 extends View
{
    @Override
    public void initGraphicObjects()
    {
        StratEdge se = StratEdge.getSE();
        tabGO = new GraphicObject[11];
        tabGO[0] = new Background(se, 0);
        tabGO[1] = new Text(se, se.getW()/2 - 300, se.getH()/2 - 400, 600, 350, 150, "Mode de jeu", "Miama.ttf", 0);
        tabGO[3] = new Text(se, se.getW()/2 - 200, se.getH()/2 - 125, 400, 100, 80, "Normale en ligne", "Miama.ttf", 0);
        tabGO[2] = new Button(se, (Text)tabGO[3], 3);
        tabGO[5] = new Text(se, se.getW()/2 - 200, se.getH()/2, 400, 100, 80, "Classique en ligne", "Miama.ttf", 0);
        tabGO[4] = new Button(se, (Text)tabGO[5], 4);
        tabGO[7] = new Text(se, se.getW()/2 - 200, se.getH()/2 + 125, 400, 100, 80, "Normale en local", "Miama.ttf", 0);
        tabGO[6] = new Button(se, (Text)tabGO[7], 5);
        tabGO[9] = new Text(se, se.getW()/2 - 200, se.getH()/2 + 250, 400, 100, 80, "Classique en local", "Miama.ttf", 0);
        tabGO[8] = new Button(se, (Text)tabGO[9], 6);
        /*tabGO[2] = new Button(se, se.getW()/2 - 400, se.getH()/2 - 125, 8, 9, 3);
        tabGO[3] = new Button(se, se.getW()/2 - 400, se.getH()/2      , 10, 11, 4);
        tabGO[4] = new Button(se, se.getW()/2 - 400, se.getH()/2 + 125, 12, 13, 5);
        tabGO[5] = new Button(se, se.getW()/2 - 400, se.getH()/2 + 250, 14, 15, 6);*/
        tabGO[10] = new Button(se, 10, 10, 0, 1, 2);
    }
}