package PST2.Online;

import PST2.Game;
import PST2.Piece.SEPiece;
import PST2.Piece.Team;
import PST2.StratEdge;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion extends Thread
{
    private static final int time = 100;                                        //Temps entre chaque envoie/réception requête
    
    private Socket connexion;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    
    private static boolean running = true; 
    private static final String S = "#";                                        //Caractère séparateur
    private static String request = "";
    public static int state = 0;
    
    //Requêtes autorisées :
    public static final String RNONE = "NONE";
    public static final String RQUEUE = "QUEUE";
    public static final String RACTION = "ACTION";
    public static final String RCANCEL = "CANCEL";

    public Connexion(String host, int port)
    {
        try{connexion = new Socket(host, port);}
        catch(UnknownHostException e){System.err.println("Connexion.Connexion : UnknownHostException");}
        catch(IOException e)
        {
            System.err.println("Impossible de se connecter au serveur " + host + " / " + port);
            running = false;
        }
    }

    @Override
    public void run()
    {
        String[] message;
        String toSend = RNONE+S+" ";
        while(running)
        {
            try
            {
                if(request.contains(S))
                {
                    toSend = request;
                    request = "";
                }
                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());
                writer.write(toSend);                                           //On envoie un message au serveur
                writer.flush();
                System.out.println("Commande " + toSend + " envoyée au serveur");
                message = read().split(S);                                      //On attend la réponse
                System.out.println("\tRéponse reçue : " + message[0] + " " + message[1]);
                
                switch(message[0])                                              //On traite la requête
                {
                    case "NONE":
                        toSend = RNONE;
                        break;
                    case "WAIT":
                        toSend = RNONE;
                        break;
                    case "MATCH":
                        state = 3;
                        initGame(message[1]);
                        toSend = RNONE;
                        break;
                    case "YOU":
                        toSend = RNONE;
                        break;
                    case "ACTION":
                        int[] act = StratEdge.r.string2Int(message[1]);
                        Game g = StratEdge.getSE().getGame();
                        g.getChecker()[act[1]][act[0]].move(act[2], act[3], g.getChecker());
                        g.setTurn();
                        toSend = RNONE;
                        break;
                    case "WIN":
                        toSend = RNONE;
                        break;
                }
                if(!toSend.contains(S))
                    toSend += S + " ";
            }
            catch(IOException e){System.err.println("Connexion.run : IOException");}
            try{sleep(time);}
            catch(InterruptedException e)
            {System.err.println("Connexion.run : InterruptedException");}
        }
        if(writer != null)
        {
            writer.write("CLOSE");
            writer.flush();
            writer.close();
        }
    }

    private String read() throws IOException                                    //Lit les réponses du serveur
    {
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }
    
    private void initGame(String mes)
    {
        Team t1, t2;
        boolean side = mes.substring(0, 1).equals("1");
        if(mes.length() > 1)
        {
            int[] team = StratEdge.r.string2Int(mes.substring(2));
            t1 = new Team(StratEdge.team, side);
            t2 = new Team(team, !side);
            StratEdge.getSE().getGame().init(t1, t2);
            StratEdge.getSE().setView(2);
        }
        else
        {
            t1 = new Team(SEPiece.getTeams()[0], side);
            t2 = new Team(SEPiece.getTeams()[0], !side);
            StratEdge.getSE().getGame().init(t1, t2);
            StratEdge.getSE().setView(2);
        }
    }
    
    public static boolean send(String request, String param)
    {
        if(!running) 
            return false;
        Connexion.request = request + S + param;
        return true;
    }
}