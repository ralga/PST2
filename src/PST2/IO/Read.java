package PST2.IO;

import PST2.StratEdge;
import processing.core.PApplet;

public class Read
{
    private final PApplet p;
    
    public Read()
    {
        p = StratEdge.getSE();
    }
    
    public int[][] matrixTextFile(String path)                                  //Charge un fichier texte constitué de nombres et dont la disposition s'effectue en matrice
    {
        String[] tabLine = p.loadStrings(path);
        int[][] data = new int[tabLine.length][];
        for(int i = 0; i < tabLine.length; i++)
            data[i] = string2Int(tabLine[i]);
        return data;
    }
    
    public String[] file(String path)                                           //Chargement d'un fichier
    {
        return p.loadStrings(path);
    }
    
    public int[] string2Int(String ch)                                          //Convertit une chaine de caractère en tableau d'entiers
    {
        String[] tabCh = ch.split(" ");
        int tabI[] = new int[tabCh.length];
        for(int i = 0; i < tabI.length; i++)
            tabI[i] = Integer.parseInt(tabCh[i]);
        return tabI;
    }
}