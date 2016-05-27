package PST2;

import PST2.UI.*;

public class Menu extends View
{
    
   @Override
   public void initGraphicObjects()
    {
        StratEdge se = StratEdge.getSE();
        tabGO = new GraphicObject[6];
        tabGO[0] = new Background(se, 0);
        tabGO[1] = new Text(se, se.getW()/2 - 300, se.getH()/2 - 350, 600, 350, 200, "Strat::Edge", "Miama.ttf", 0);
        /*tabGO[2] = new Button(se, se.getW()/2 - 200, se.getH()/2 - 30, 4, 5, 0);
        tabGO[3] = new Button(se, se.getW()/2 - 200, se.getH()/2 + 200, 6, 7, 1);*/
        tabGO[3] = new Text(se, se.getW()/2 - 200, se.getH()/2 - 30, 400, 200, 150, "Jouer", "Miama.ttf", 0);
        tabGO[2] = new Button(se, (Text)tabGO[3], 0);
        tabGO[5] = new Text(se, se.getW()/2 - 150, se.getH()/2 + 200, 300, 130, 80, "Quitter", "Miama.ttf", 0);
        tabGO[4] = new Button(se, (Text)tabGO[5], 1);
    }
}
