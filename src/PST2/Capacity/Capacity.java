package PST2.Capacity;

import PST2.Piece.Piece;
import PST2.StratEdge;
import java.util.ArrayList;

public abstract class Capacity {

    private static ArrayList<Capacity> passive1 = new ArrayList<>();
    private static ArrayList<Capacity> active = new ArrayList<>();
    private static ArrayList<Capacity> passive2 = new ArrayList<>();
    Piece piece;                                                                //Pièce affiliée à la capacité
    protected int cooldown;                                                     //Cooldown en cours
    protected int cast;                                                         //Cast en cours
    protected boolean isActive;                                                 //Indique si la capacité est active ou passive

    public Capacity(Piece piece, int cooldown, int cast, boolean active) {
        this.cooldown = cooldown;
        this.cast = cast;
        this.isActive = active;
        this.piece = piece;
    }

    public void init() {                                                         //ajoute le pouvoir dans la liste des pouvoirs initialisés
        if (!isActive)
            if (piece.getTeam())
                passive1.add(this);
            else
                passive2.add(this);
        else
            active.add(this);

    }

    public boolean isAvailable() {                                              //Indique si le pouvoir est disponible
        return (cooldown == 0);
    }
    
    protected ArrayList<Piece> getAround(int x, int y){                         //Renvoie les cases occupées autour de la case [x,y]
        ArrayList<Piece> around = new ArrayList<>();
        Piece[][] tab = StratEdge.getSE().getGame().getChecker();
        for (int i = x - 1; i <= x + 1; ++i)
            for (int j = y - 1; j <= y + 1; ++j)
                try {
                    if (tab[j][i] != null)
                        around.add(tab[j][i]);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Out of Bound exception : " + this.getClass().getName());
                }
        return around;
    }
    
    public abstract void power();

    /**
     * Permet de lier le numéro a une capacité
     *
     * @param p = Pièce liée à cette capacité
     * @param i = numéro de la capacité
     * @return Retourne une nouvelle instance d'une capacité
     */
    public static Capacity getCapacity(Piece p, int i) {
        switch (i) {
            case 0:
                return new Regen(p);                                            //Capacité de régénération
            case 1:
                return new Teleport(p);                                         //Capacité de téléportation
            case 2:
                return new Caract(p,true);                                      //Capacité d'augmentation des caractéristiques
            case 3:
                return new Caract(p,false);                                     //Capacité de diminution des caractéristiques
            default:
                return null;
        }
    }

    //Getters
    public int getCurrentCool() {return cooldown;}

    public int getCurrentCast() {return cast;}

    public static ArrayList<Capacity> getPassive(boolean team) {
        if (team)
            return passive1;
        else
            return passive2;
    }
    
    public static ArrayList<Capacity> getActive(){return active;}

    //Setters
    /**
     * Décrémente de 1 le cooldown, utile pour passer le tours
     */
    public void minusCool() {
        if (this.cooldown > 0)
            --this.cooldown;
    }

}
