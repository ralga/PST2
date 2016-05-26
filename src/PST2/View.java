package PST2;

import PST2.UI.Button;
import PST2.UI.GraphicObject;
import java.util.ArrayList;

public abstract class View
{
    protected GraphicObject[] tabGO;                                            //Tableau contenant tous les objets graphiques de la view
    protected ArrayList<Button> butt= new ArrayList<>();;
    
    public GraphicObject[] getGO()
    {
        if(tabGO == null)
        {
            initGraphicObjects();
            for(GraphicObject go : tabGO)
                go.init();
        }
        return tabGO;
    }
    
    public ArrayList<Button> getButts(){return butt;}
    
    public abstract void initGraphicObjects();
}
