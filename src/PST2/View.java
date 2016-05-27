package PST2;

import PST2.UI.GraphicObject;

public abstract class View
{
    protected GraphicObject[] tabGO;                                            //Tableau contenant tous les objets graphiques de la view
    
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
    
    public abstract void initGraphicObjects();
}
